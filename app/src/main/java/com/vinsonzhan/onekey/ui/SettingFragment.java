/*
 *  ******************************* Copyright (c)*********************************\
 *  **
 *  ** (c) Copyright 2018,VinsonZhan, china, wuhan
 *  ** All Rights Reserved
 *  **
 *  ** By( The OneKey Project)
 *  **
 *  *********************************版本信息*******************************
 *  ** 版 本: V0.1
 *  ***********************************************************************
 *  ********************************End of Head************************************\
 *
 */

package com.vinsonzhan.onekey.ui;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.socks.library.KLog;
import com.vinsonzhan.onekey.R;
import com.vinsonzhan.onekey.util.Utils;

/**
 * project:onekey
 * email：zhanwjwit@163.com
 * time：2/7/18 2:05 PM
 * author：Vinson.Zhan
 * comment：
 */
public class SettingFragment extends PreferenceFragment {

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceManager().setSharedPreferencesMode(Context.MODE_PRIVATE);
        addPreferencesFromResource(R.xml.pref_header);
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findPreference("changeSecret").setOnPreferenceClickListener(new Preference
                .OnPreferenceClickListener() {
            @Override public boolean onPreferenceClick(Preference preference) {
                KLog.d(preference.getKey());
                return false;
            }
        });

        final SwitchPreference randomKeyBoard = (SwitchPreference) findPreference("randomKeyBoard");
        randomKeyBoard.setOnPreferenceChangeListener(new Preference
                .OnPreferenceChangeListener() {
            @Override public boolean onPreferenceChange(Preference preference, Object newValue) {
                KLog.d(newValue);
                if (newValue.equals(true))
                    randomKeyBoard.setChecked(true);
                else randomKeyBoard.setChecked(false);
                return false;
            }
        });

        findPreference("fingerPrint").setOnPreferenceChangeListener(new Preference
                .OnPreferenceChangeListener() {
            @Override public boolean onPreferenceChange(Preference preference, Object newValue) {
                KLog.d(newValue);
                return false;
            }
        });

        findPreference("record").setOnPreferenceClickListener(new Preference
                .OnPreferenceClickListener() {
            @Override public boolean onPreferenceClick(Preference preference) {
                KLog.d(preference.getKey());
                return false;
            }
        });

        findPreference("weixin").setOnPreferenceClickListener(new Preference
                .OnPreferenceClickListener() {
            @Override public boolean onPreferenceClick(Preference preference) {
                KLog.d(preference.getKey());
                return false;
            }
        });

        findPreference("phone").setOnPreferenceClickListener(new Preference
                .OnPreferenceClickListener() {
            @Override public boolean onPreferenceClick(Preference preference) {
                KLog.d(preference.getKey());
                return false;
            }
        });

        findPreference("backup").setOnPreferenceClickListener(new Preference
                .OnPreferenceClickListener() {
            @Override public boolean onPreferenceClick(Preference preference) {
                KLog.d(preference.getKey());
                return false;
            }
        });

        findPreference("restore").setOnPreferenceClickListener(new Preference
                .OnPreferenceClickListener() {
            @Override public boolean onPreferenceClick(Preference preference) {
                KLog.d(preference.getKey());
                return false;
            }
        });

        findPreference("sendData").setOnPreferenceClickListener(new Preference
                .OnPreferenceClickListener() {
            @Override public boolean onPreferenceClick(Preference preference) {
                KLog.d(preference.getKey());
                return false;
            }
        });

        findPreference("vip").setOnPreferenceClickListener(new Preference
                .OnPreferenceClickListener() {
            @Override public boolean onPreferenceClick(Preference preference) {
                KLog.d(preference.getKey());
                return false;
            }
        });

        findPreference("feedback").setOnPreferenceClickListener(new Preference
                .OnPreferenceClickListener() {
            @Override public boolean onPreferenceClick(Preference preference) {
                KLog.d(preference.getKey());
                return false;
            }
        });

        findPreference("copyright").setOnPreferenceClickListener(new Preference
                .OnPreferenceClickListener() {
            @Override public boolean onPreferenceClick(Preference preference) {
                KLog.d(preference.getKey());
                return false;
            }
        });
    }
}
