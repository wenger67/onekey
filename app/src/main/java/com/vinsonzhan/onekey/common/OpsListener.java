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

import com.vinsonzhan.onekey.adapter.OpsType;
import com.vinsonzhan.onekey.model.Account;
import com.vinsonzhan.onekey.model.Category;

import eu.davidea.flexibleadapter.items.IFlexible;

/**
 * project:onekey
 * email：zhanwjwit@163.com
 * time：2/2/18 11:17 AM
 * author：Vinson.Zhan
 * comment：
 */
public interface OpsListener {
    void onOpsEvent(IFlexible data, @OpsType int opsType);
}