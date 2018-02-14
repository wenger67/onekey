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

package com.vinsonzhan.onekey.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * project:onekey
 * email：zhanwjwit@163.com
 * time：2/9/18 10:05 AM
 * author：Vinson.Zhan
 * comment：
 */
public class Utils {

    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();

        }

    }
}
