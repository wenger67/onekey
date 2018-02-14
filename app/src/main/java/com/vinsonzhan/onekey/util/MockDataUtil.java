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

import android.os.SystemClock;

import com.socks.library.KLog;
import com.vinsonzhan.onekey.App;
import com.vinsonzhan.onekey.greendao.AccountDao;
import com.vinsonzhan.onekey.model.Account;
import com.vinsonzhan.onekey.model.Category;

import org.greenrobot.greendao.query.WhereCondition;

import java.util.ArrayList;
import java.util.Random;

/**
 * project:onekey
 * email：zhanwjwit@163.com
 * time：2/6/18 4:21 PM
 * author：Vinson.Zhan
 * comment：
 */
public class MockDataUtil {
    public static int BANK_COUNT = 123;
    public static int EMAIL_COUNT = 1000;
    public static int GAME_COUNT = 30;
    public static int MAP_COUNT = 5;
    public static int SHOP_COUNT = 1;

    public static void insertFackAccount(){
        ArrayList<Account> accounts = new ArrayList<>();
        for (int i = 0; i < EMAIL_COUNT; i++) {
            long currentTime = SystemClock.currentThreadTimeMillis();
            String title = getRandomChinese(4) + "邮箱";
            String userName = generateString(6) + "@163.com";
            String password = generateString(8);
            String comment = getRandomChinese(50);
            Account account = new Account(null, Constants.CATAGORY_MAP.get(Constants.EMAIL),
                    title, userName, password, comment, currentTime, currentTime);
            accounts.add(account);
        }
        App.getDaoSession().getAccountDao().insertInTx(accounts);
        WhereCondition whereCondition = AccountDao.Properties.Category.eq(Constants.CATAGORY_MAP
                .get(Constants.EMAIL));
        long count = App.getDaoSession().getAccountDao().queryBuilder().where(whereCondition)
                .count();
        Category category = DataUtil.getCategoryByName(Constants.CATAGORY_MAP
                .get(Constants.EMAIL));
        KLog.d(count);
        category.setCount((int)count);
        App.getDaoSession().getCategoryDao().update(category);


        ArrayList<Account> banks = new ArrayList<>();
        for (int i = 0; i < BANK_COUNT; i++) {
            long currentTime = SystemClock.currentThreadTimeMillis();
            String title = getRandomChinese(4) + "银行";
            String userName = "622848" + generateNumber(10);
            String password = generateString(8);
            String comment = getRandomChinese(50);
            Account account = new Account(null, Constants.CATAGORY_MAP.get(Constants.BANK),
                    title, userName, password, comment, currentTime, currentTime);
            banks.add(account);
        }
        App.getDaoSession().getAccountDao().insertInTx(banks);
        WhereCondition whereCondition1 = AccountDao.Properties.Category.eq(Constants.CATAGORY_MAP
                .get(Constants.BANK));
        long count1 = App.getDaoSession().getAccountDao().queryBuilder().where(whereCondition1)
                .count();
        Category category1 = DataUtil.getCategoryByName(Constants.CATAGORY_MAP
                .get(Constants.BANK));
        KLog.d(count1);
        category1.setCount((int)count1);
        App.getDaoSession().getCategoryDao().update(category1);


        ArrayList<Account> games = new ArrayList<>();
        for (int i = 0; i < GAME_COUNT; i++) {
            long currentTime = SystemClock.currentThreadTimeMillis();
            String title = getRandomChinese(4) + "争霸";
            String userName = generateString(6);
            String password = generateString(8);
            String comment = getRandomChinese(50);
            Account account = new Account(null, Constants.CATAGORY_MAP.get(Constants.GAME),
                    title, userName, password, comment, currentTime, currentTime);
            games.add(account);
        }
        App.getDaoSession().getAccountDao().insertInTx(games);
        WhereCondition whereCondition2 = AccountDao.Properties.Category.eq(Constants.CATAGORY_MAP
                .get(Constants.GAME));
        long count2 = App.getDaoSession().getAccountDao().queryBuilder().where(whereCondition2)
                .count();
        Category category2 = DataUtil.getCategoryByName(Constants.CATAGORY_MAP
                .get(Constants.GAME));
        KLog.d(count2);
        category2.setCount((int)count2);
        App.getDaoSession().getCategoryDao().update(category2);


        ArrayList<Account> maps = new ArrayList<>();
        for (int i = 0; i < MAP_COUNT; i++) {
            long currentTime = SystemClock.currentThreadTimeMillis();
            String title = getRandomChinese(4) + "导航";
            String userName = generateString(6);
            String password = generateString(8);
            String comment = getRandomChinese(50);
            Account account = new Account(null, Constants.CATAGORY_MAP.get(Constants.MAP),
                    title, userName, password, comment, currentTime, currentTime);
            maps.add(account);
        }
        App.getDaoSession().getAccountDao().insertInTx(maps);
        WhereCondition whereCondition3 = AccountDao.Properties.Category.eq(Constants.CATAGORY_MAP
                .get(Constants.MAP));
        long count3 = App.getDaoSession().getAccountDao().queryBuilder().where(whereCondition3)
                .count();
        Category category3 = DataUtil.getCategoryByName(Constants.CATAGORY_MAP
                .get(Constants.MAP));
        KLog.d(count3);
        category3.setCount((int)count3);
        App.getDaoSession().getCategoryDao().update(category3);

        ArrayList<Account> shops = new ArrayList<>();
        for (int i = 0; i < SHOP_COUNT; i++) {
            long currentTime = SystemClock.currentThreadTimeMillis();
            String title = getRandomChinese(4) + "购物";
            String userName = generateString(6);
            String password = generateString(8);
            String comment = getRandomChinese(50);
            Account account = new Account(null, Constants.CATAGORY_MAP.get(Constants.SHOPPING),
                    title, userName, password, comment, currentTime, currentTime);
            shops.add(account);
        }
        App.getDaoSession().getAccountDao().insertInTx(shops);
        WhereCondition whereCondition4 = AccountDao.Properties.Category.eq(Constants.CATAGORY_MAP
                .get(Constants.SHOPPING));
        long count4 = App.getDaoSession().getAccountDao().queryBuilder().where(whereCondition4)
                .count();
        Category category4 = DataUtil.getCategoryByName(Constants.CATAGORY_MAP
                .get(Constants.SHOPPING));
        KLog.d(count4);
        category4.setCount((int)count4);
        App.getDaoSession().getCategoryDao().update(category4);
    }

    public static final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String letterChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String numberChar = "0123456789";

    /**
     * 产生长度为length的随机字符串（包括字母和数字）
     * @param length
     * @return
     */
    public static String generateString(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(allChar.charAt(random.nextInt(allChar.length())));
        }
        return sb.toString();
    }
    /**
     * 产生长度为length的随机字符串（包括字母，不包括数字）
     * @param length
     * @return
     */
    public static String generateMixString(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(letterChar.charAt(random.nextInt(letterChar.length())));
        }
        return sb.toString();
    }
    /**
     * 产生长度为length的随机小写字符串（包括字母，不包括数字）
     * @param length
     * @return
     */
    public static String generateLowerString(int length) {
        return generateMixString(length).toLowerCase();
    }
    /**
     * 产生长度为length的随机大写字符串（包括字母，不包括数字）
     * @param length
     * @return
     */
    public static String generateUpperString(int length) {
        return generateMixString(length).toUpperCase();
    }

    public static String generateNumber(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(numberChar.charAt(random.nextInt(numberChar.length())));
        }
        return sb.toString();
    }

    public static String getRandomChinese(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i ++) {
            sb.append((char) (0x4e00 + (int) (Math.random() * (0x9fa5 - 0x4e00 + 1))));
        }
        return sb.toString();
    }


}