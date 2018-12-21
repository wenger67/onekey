package com.vinsonzhan.onekey.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.blankj.utilcode.util.EncryptUtils;
import com.socks.library.KLog;
import com.vinsonzhan.onekey.App;

/**
 * Created by vinsonzhan on 1/18/18.
 */

public class PreferenceUtils {

    private static final String PREF_ONEKEY = "pref_onekey";

    private static final String PREF_KEY_UNLOCK_PATTERN = "key_unlock_pattern";
    private static final String PREF_VALUE_UNLOCK_PATTERN_DEFAULT = "-1";

    private static final String PREF_KEY_DB_INIT = "key_db_init";
    private static final String PREF_KEY_DB_MOCK = "key_db_mock";

    private static SharedPreferences getSP() {
        return App.getInstance().getSharedPreferences(PREF_ONEKEY, Context.MODE_PRIVATE);
    }

    public static boolean hasUnlockPattern() {
        return !getSP().getString(PREF_KEY_UNLOCK_PATTERN, PREF_VALUE_UNLOCK_PATTERN_DEFAULT).equals(PREF_VALUE_UNLOCK_PATTERN_DEFAULT);
    }

    public static String getUnlockPattern() {
        return getSP().getString(PREF_KEY_UNLOCK_PATTERN, PREF_VALUE_UNLOCK_PATTERN_DEFAULT);
    }

    public static void setUnlockPattern(String key) {
        SharedPreferences.Editor editor = getSP().edit();
        String sha512 = EncryptUtils.encryptSHA512ToString(key.getBytes());
        KLog.d(key + " --> " + sha512);
        editor.putString(PREF_KEY_UNLOCK_PATTERN, sha512);
        editor.apply();
    }

    public static boolean compareUnlockPattern(String key) {
        String sha512 = getUnlockPattern();
        return sha512.equals(EncryptUtils.encryptSHA512ToString(key));
    }

    public static void resetUnlockPattern() {
        SharedPreferences.Editor editor = getSP().edit();
        editor.putString(PREF_KEY_UNLOCK_PATTERN, PREF_VALUE_UNLOCK_PATTERN_DEFAULT);
        editor.apply();
    }

    public static boolean getDbInitialed() {
        return getSP().getBoolean(PREF_KEY_DB_INIT, false);
    }

    public static void setDbInitialed(boolean flag) {
        SharedPreferences.Editor editor = getSP().edit();
        editor.putBoolean(PREF_KEY_DB_INIT, flag);
        editor.apply();
    }

    public static boolean getMockDataFlag() {
        return getSP().getBoolean(PREF_KEY_DB_MOCK, false);
    }

    public static void setMockDataFlag(boolean flag) {
        SharedPreferences.Editor editor = getSP().edit();
        editor.putBoolean(PREF_KEY_DB_MOCK, flag);
        editor.apply();
    }

}
