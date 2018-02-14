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

import com.vinsonzhan.onekey.App;
import com.vinsonzhan.onekey.R;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * project:onekey
 * email：zhanwjwit@163.com
 * time：1/19/18 7:00 PM
 * author：Vinson.Zhan
 * comment：
 *
 */
public class Constants {
    public static final String EMAIL = "fa_envelope_square";
    public static final String NOTE = "fa_file_text";
    public static final String WEBSITE= "fa_internet_explorer";
    public static final String GAME = "fa_gamepad";
    public static final String CHAT = "fa_commenting";
    public static final String BANK = "fa_bank";
    public static final String BUS = "fa_bus";
    public static final String SHOPPING = "fa_shopping_cart";
    public static final String MONEY = "fa_money";
    public static final String BOOK = "fa_newspaper_o";
    public static final String HOME = "fa_home";
    public static final String WALLET = "fa_credit_card";
    public static final String MAP = "fa_map";
    public static final String HEALTH = "fa_medkit";
    public static final String SPORT = "fa_soccer_ball_o";
    public static final String VIDEO = "fa_file_video_o";
    public static final String MEDIA = "fa_video_camera";
    public static final String TOOLS = "fa_wrench";
    public static final String COMPANY = "fa_vcard_o";
    public static final String OTHERS = "fa_send_o";

    // icon --- name
    public static final Map<String, String> CATAGORY_MAP = new HashMap<>();

    static {
        CATAGORY_MAP.put(EMAIL, App.getInstance().getString(R.string.category_email));
        CATAGORY_MAP.put(NOTE, App.getInstance().getString(R.string.category_note));
        CATAGORY_MAP.put(WEBSITE, App.getInstance().getString(R.string.category_website));
        CATAGORY_MAP.put(GAME, App.getInstance().getString(R.string.category_game));
        CATAGORY_MAP.put(CHAT, App.getInstance().getString(R.string.category_chat));
        CATAGORY_MAP.put(BANK, App.getInstance().getString(R.string.category_bank));
        CATAGORY_MAP.put(BUS, App.getInstance().getString(R.string.category_bus));
        CATAGORY_MAP.put(SHOPPING, App.getInstance().getString(R.string.category_shopping));
        CATAGORY_MAP.put(MONEY, App.getInstance().getString(R.string.category_money));
        CATAGORY_MAP.put(BOOK, App.getInstance().getString(R.string.category_book));
        CATAGORY_MAP.put(HOME, App.getInstance().getString(R.string.category_home));
        CATAGORY_MAP.put(WALLET, App.getInstance().getString(R.string.category_wallet));
        CATAGORY_MAP.put(MAP, App.getInstance().getString(R.string.category_map));
        CATAGORY_MAP.put(HEALTH, App.getInstance().getString(R.string.category_health));
        CATAGORY_MAP.put(SPORT, App.getInstance().getString(R.string.category_sport));
        CATAGORY_MAP.put(VIDEO, App.getInstance().getString(R.string.category_video));
        CATAGORY_MAP.put(MEDIA, App.getInstance().getString(R.string.category_media));
        CATAGORY_MAP.put(TOOLS, App.getInstance().getString(R.string.category_tools));
        CATAGORY_MAP.put(COMPANY, App.getInstance().getString(R.string.category_company));
        CATAGORY_MAP.put(OTHERS, App.getInstance().getString(R.string.category_others));
    }
}
