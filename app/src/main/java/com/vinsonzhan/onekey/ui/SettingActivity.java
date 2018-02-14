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

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.beardedhen.androidbootstrap.font.FontAwesome;
import com.vinsonzhan.onekey.R;

/**
 * project:onekey
 * email：zhanwjwit@163.com
 * time：1/24/18 7:18 PM
 * author：Vinson.Zhan
 * comment：
 */
public class SettingActivity extends BaseActivity {

    @Override protected void onDestroy() {
        super.onDestroy();
    }

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.setting));
        setIcon(FontAwesome.FA_ARROW_LEFT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setupActionBar();

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.pref_fragment, new SettingFragment()).commit();
    }


}
