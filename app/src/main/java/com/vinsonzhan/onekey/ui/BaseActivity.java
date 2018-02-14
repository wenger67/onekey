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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.socks.library.KLog;
import com.vinsonzhan.onekey.App;
import com.vinsonzhan.onekey.R;
import com.vinsonzhan.onekey.common.StartMode;

/**
 * project:onekey
 * email：zhanwjwit@163.com
 * time：1/24/18 7:16 PM
 * author：Vinson.Zhan
 * comment：
 */
public class BaseActivity extends AppCompatActivity {

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerReceiver(mHomeKeyEventReceiver, new IntentFilter(
                Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
    }

    String titleStr;
    String iconStr;

    public void setIcon(String icon) {
        this.iconStr = icon;
    }

    public void setTitle(String title) {
        this.titleStr = title;
    }

    protected void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            KLog.d("actionBar is null");
            return;
        }
        View view = LayoutInflater.from(this).inflate(R.layout.action_bar, null);
        AwesomeTextView icon = view.findViewById(R.id.back);
        AwesomeTextView title = view.findViewById(R.id.title);
        if (iconStr == null || iconStr.isEmpty())
            icon.setVisibility(View.INVISIBLE);
        else {
            icon.setVisibility(View.VISIBLE);
            icon.setFontAwesomeIcon(iconStr);
        }

        if (titleStr == null || titleStr.isEmpty())
            title.setVisibility(View.INVISIBLE);
        else {
            icon.setVisibility(View.VISIBLE);
            title.setText(titleStr);
        }

        icon.setClickable(true);
        icon.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                onBackPressed();
            }
        });

        actionBar.setCustomView(view);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
    }

    private BroadcastReceiver mHomeKeyEventReceiver = new BroadcastReceiver() {
        String SYSTEM_REASON = "reason";
        String SYSTEM_HOME_KEY = "homekey";
        String SYSTEM_HOME_KEY_LONG = "recentapps";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason = intent.getStringExtra(SYSTEM_REASON);
                if (TextUtils.equals(reason, SYSTEM_HOME_KEY)) {
                    KLog.d("home key to bg");
                    App.getInstance().setStartMode(StartMode.START_HOME_KEY_BG);
                }else if(TextUtils.equals(reason, SYSTEM_HOME_KEY_LONG)){
                    KLog.d("home key to recent apps");
                }
            }
        }
    };

    @Override protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mHomeKeyEventReceiver);
    }
}
