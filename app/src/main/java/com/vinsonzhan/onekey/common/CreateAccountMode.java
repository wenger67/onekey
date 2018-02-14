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

import static com.vinsonzhan.onekey.common.CreateAccountMode.CREATE;
import static com.vinsonzhan.onekey.common.CreateAccountMode.MODIFY;

/**
 * project:onekey
 * email：zhanwjwit@163.com
 * time：2/1/18 6:32 PM
 * author：Vinson.Zhan
 * comment：
 */
@IntDef({CREATE, MODIFY})
@Retention(RetentionPolicy.SOURCE)
public @interface CreateAccountMode {
    int CREATE = 0;
    int MODIFY = 1;
}
