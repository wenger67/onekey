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

package com.vinsonzhan.onekey.common;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.vinsonzhan.onekey.common.StartMode.START_BACK_KEY_BG;
import static com.vinsonzhan.onekey.common.StartMode.START_HOME_KEY_BG;
import static com.vinsonzhan.onekey.common.StartMode.START_NORMAL;

/**
 * project:onekey
 * email：zhanwjwit@163.com
 * time：1/19/18 2:22 PM
 * author：Vinson.Zhan
 * comment：
 */
@IntDef({START_NORMAL, START_HOME_KEY_BG, START_BACK_KEY_BG})
@Retention(RetentionPolicy.SOURCE)
public @interface StartMode {
    int START_NORMAL = 0;
    int START_HOME_KEY_BG = 1;
    int START_BACK_KEY_BG = 2;
}
