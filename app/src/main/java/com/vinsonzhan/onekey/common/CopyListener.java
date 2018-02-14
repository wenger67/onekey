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

/**
 * project:onekey
 * email：zhanwjwit@163.com
 * time：2/6/18 4:00 PM
 * author：Vinson.Zhan
 * comment：
 */
public interface CopyListener {

    void onAccountTitleCopied(String title);
    void onAccountUsernameCopied(String username);
    void onAccountPasswordCopied(String password);
    void onAccountCommentCopied(String conment);
}
