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

package com.vinsonzhan.onekey.setting;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vinsonzhan.onekey.R;
import com.vinsonzhan.onekey.util.Utils;

/**
 * project:onekey
 * email：zhanwjwit@163.com
 * time：2/9/18 11:21 AM
 * author：Vinson.Zhan
 * comment：
 */
public class VersionPreference extends Preference {
    Context context;

    public VersionPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }


    public VersionPreference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VersionPreference(Context context) {
        this(context, null);
    }

    @Override protected View onCreateView(ViewGroup parent) {
        super.onCreateView(parent);
        return LayoutInflater.from(context).inflate(R.layout.pref_version, null);
    }

    @Override protected void onBindView(View view) {
        super.onBindView(view);
        TextView title = view.findViewById(R.id.title);
        TextView value = view.findViewById(R.id.value);
        title.setText(context.getString(R.string.version));
        value.setText(Utils.getVersion(context));
    }
}
