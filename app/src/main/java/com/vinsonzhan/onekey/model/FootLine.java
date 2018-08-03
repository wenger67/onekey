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

package com.vinsonzhan.onekey.model;

import com.vinsonzhan.onekey.adapter.ItemType;

/**
 * project:onekey
 * email：zhanwjwit@163.com
 * time：2/1/18 9:53 AM
 * author：Vinson.Zhan
 * comment： just a divider line at the bottom
 */
public class FootLine extends Account {

    public FootLine() {
    }

    @Override
    public int getItemViewType() {
        return ItemType.TYPE_ACCOUNT_FOOT;
    }
}
