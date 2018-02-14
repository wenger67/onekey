package com.vinsonzhan.onekey.util;

import android.content.Context;
import android.content.Loader;
import android.content.SharedPreferences;
import android.text.BoringLayout;

import com.blankj.utilcode.util.EncryptUtils;
import com.socks.library.KLog;

import java.util.Arrays;

/**
 * Created by vinsonzhan on 1/18/18.
 */

public class PreferenceUtils {

    public static final String PREF_LOCK = "pref_lock";
    public static final String PREF_LOCK_KEY = "lock_key";
    public static final String PREF_LOCK_KEY_DEFAULT = "-1";

    public static final String PREF_DB = "pref_db";
    public static final String PREF_DB_INIT = "db_init";
    public static final String PREF_DB_MOCK = "db_mock";

    public static boolean isLockExist(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PREF_LOCK, Context.MODE_PRIVATE);
        return !sp.getString(PREF_LOCK_KEY, PREF_LOCK_KEY_DEFAULT).equals(PREF_LOCK_KEY_DEFAULT);
    }

    public static String readLockKey(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PREF_LOCK, Context.MODE_PRIVATE);
        return sp.getString(PREF_LOCK_KEY, PREF_LOCK_KEY_DEFAULT);
    }

    public static void saveLockKey(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(PREF_LOCK, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        String sha512 = EncryptUtils.encryptSHA512ToString(key.getBytes());
        KLog.d(key + " --> " + sha512);
        editor.putString(PREF_LOCK_KEY, sha512);
        editor.apply();
    }

    public static boolean compareLockKey(Context context, String key) {
        String sha512 = readLockKey(context);
        return sha512.equals(EncryptUtils.encryptSHA512ToString(key));
    }

    public static void clearLockKey(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PREF_LOCK, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }

    public static boolean isDbInitialed(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PREF_DB, Context.MODE_PRIVATE);
        return sp.getBoolean(PREF_DB_INIT, false);
    }

    public static void saveDbInitialed(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PREF_DB, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(PREF_DB_INIT, true);
        editor.apply();
    }

    public static boolean isMockDataInit(Context c) {
        SharedPreferences sp = c.getSharedPreferences(PREF_DB, Context.MODE_PRIVATE);
        return sp.getBoolean(PREF_DB_MOCK, false);
    }

    public static void saveMockDataInit(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PREF_DB, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(PREF_DB_MOCK, true);
        editor.apply();
    }

}
