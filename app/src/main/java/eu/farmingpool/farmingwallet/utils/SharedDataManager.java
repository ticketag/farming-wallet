package eu.farmingpool.farmingwallet.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import eu.farmingpool.farmingwallet.application.GlobalApplication;
import eu.farmingpool.farmingwallet.transactions.TransactionRecords;
import eu.farmingpool.farmingwallet.wallet.Wallet;

public class SharedDataManager {
    private static final String NAME = "preferences";
    private static final SharedPreferences sharedPreferences = GlobalApplication.getAppContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);

    public static void putString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getSharedString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public static void putTransactionRecords(String key, TransactionRecords transactionRecords) {
        putObject(key, transactionRecords);
    }

    public static TransactionRecords getSharedTransactionRecords(String key) {
        return getObject(key, TransactionRecords.class);
    }

    public static void putWallet(String key, Wallet wallet) {
        putObject(key, wallet);
    }

    public static Wallet getWallet(String key) {
        return getObject(key, Wallet.class);
    }

    private static <T> T getObject(String key, Class<T> objectType) {
        try {
            String json = getSharedString(key, "");

            if ((json == null) || (json.length() == 0)) {
                return null;
            }

            Gson gson = new Gson();

            return gson.fromJson(json, objectType);
        } catch (JsonSyntaxException jsonSyntaxException) {
            return null;
        }
    }

    private static <T> void putObject(String key, T object) {
        Gson gson = new Gson();
        String json = gson.toJson(object);

        putString(key, json);
    }
}
