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
import com.vinsonzhan.onekey.greendao.AccountDao;
import com.vinsonzhan.onekey.greendao.CategoryDao;
import com.vinsonzhan.onekey.model.Account;
import com.vinsonzhan.onekey.model.Category;

import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

/**
 * project:onekey
 * email：zhanwjwit@163.com
 * time：1/26/18 1:28 PM
 * author：Vinson.Zhan
 * comment：
 */
public class DataUtil {

    public static final String DB_NAME = "onekey.db";

    /**
     *  the max count of categories that displayed when launch app at the first time
     */
    public static final int MAX_DEFAULT_CATEGORY_COUNT = 5;

    public static List<Category> getData() {
        QueryBuilder<Category> categoryQb = App.getDaoSession().getCategoryDao().queryBuilder();
        WhereCondition condition1 = CategoryDao.Properties.IsDefault.eq(true);
        WhereCondition condition2 = CategoryDao.Properties.Count.gt(0);
        return categoryQb.where(condition1, condition2).list();
    }

    public static Category getCategoryByName(String name) {
        QueryBuilder<Category> qb = App.getDaoSession().getCategoryDao().queryBuilder();
        WhereCondition condition = CategoryDao.Properties.Name.eq(name);
        return qb.where(condition).unique();
    }

    public static List<Account> getAccountsByCategory(Category category) {
        QueryBuilder<Account> qb = App.getDaoSession().getAccountDao().queryBuilder();
        WhereCondition condition = AccountDao.Properties.Category.eq(category.getName());
        List<Account> accounts = qb.where(condition).list();
        for (Account account: accounts ) {
            account.setSwipeable(true);
        }
        return accounts;
    }

    public static Account getAccountById(Long id) {
        QueryBuilder<Account> qb = App.getDaoSession().getAccountDao().queryBuilder();
        WhereCondition condition = AccountDao.Properties.Id.eq(id);
        return qb.where(condition).unique();
    }
}
