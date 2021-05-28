package eu.farmingpool.farmingwallet.utils;

import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.security.GeneralSecurityException;

import eu.farmingpool.farmingwallet.accounts.Accounts;
import eu.farmingpool.farmingwallet.application.GlobalApplication;
import eu.farmingpool.farmingwallet.keywords.Keywords;

public class EncryptedSharedDataManager {
    private static final String NAME = "encryptedPreferences";
    private static final String MASTER_KEY_ALIAS = "masterKey";
    private static final String KEY_ACCOUNTS = "accounts";
    private static SharedPreferences sharedPreferences;

    static {
        try {
            sharedPreferences = EncryptedSharedPreferences.create(
                    NAME,
                    MASTER_KEY_ALIAS,
                    GlobalApplication.getAppContext(),
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void putString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getSharedString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public static void putKeywords(String key, Keywords keywords) {
        putObject(key, keywords);
    }

    public static Keywords getSharedKeywords(String key) {
        return getSharedObject(key, Keywords.class);
    }

    public static void putAccounts(Accounts accounts) {
        putObject(KEY_ACCOUNTS, accounts);
    }

    public static Accounts getAccounts() {
        return getSharedObject(KEY_ACCOUNTS, Accounts.class);
    }

    private static <T> T getSharedObject(String key, Class<T> objectType) {
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
