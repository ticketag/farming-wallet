package eu.farmingpool.farmingwallet.utils;

import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.security.GeneralSecurityException;

import eu.farmingpool.farmingwallet.GlobalApplication;
import eu.farmingpool.farmingwallet.keywords.Keywords;

public class SharedDataManager {
    private static final String NAME = "preferences";
    private static final String MASTER_KEY_ALIAS = "masterKey";
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

    public static void putSharedString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getSharedString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public static void putSharedKeywords(String key, Keywords keywords) {
        putSharedObject(key, keywords);
    }

    public static Keywords getSharedKeywords(String key) {
        return getSharedObject(key, Keywords.class);
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

    private static <T> void putSharedObject(String key, T object) {
        Gson gson = new Gson();
        String json = gson.toJson(object);

        putSharedString(key, json);
    }
}
