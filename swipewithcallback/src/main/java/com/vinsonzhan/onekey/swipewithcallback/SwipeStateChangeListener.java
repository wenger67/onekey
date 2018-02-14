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

package com.vinsonzhan.onekey.swipewithcallback;

/**
 * project:onekey
 * email：zhanwjwit@163.com
 * time：1/30/18 5:26 PM
 * author：Vinson.Zhan
 * comment：
 */
public interface SwipeStateChangeListener {
    void onStateChange(State newState, State oldState);
}
