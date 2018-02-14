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
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.Toast;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.Utils;
import com.socks.library.KLog;
import com.vinsonzhan.onekey.App;
import com.vinsonzhan.onekey.common.StartMode;

/**
 * project:onekey
 * email：zhanwjwit@163.com
 * time：1/19/18 10:24 AM
 * author：Vinson.Zhan
 * comment：
 */
public class BaseExitActivity extends BaseActivity {

    long firstTime = 0;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (firstTime == 0) {
                firstTime = SystemClock.currentThreadTimeMillis();
                Toast.makeText(getApplicationContext(), "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
            } else if ((SystemClock.currentThreadTimeMillis() - firstTime) < 2000){
                App.getInstance().setStartMode(StartMode.START_BACK_KEY_BG);
                ActivityUtils.finishAllActivities();
            } else {
                firstTime = 0;
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
