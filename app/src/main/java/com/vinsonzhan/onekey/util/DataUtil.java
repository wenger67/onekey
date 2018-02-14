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

import com.chad.library.adapter.base.entity.IExpandable;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.socks.library.KLog;
import com.vinsonzhan.onekey.App;
import com.vinsonzhan.onekey.greendao.AccountDao;
import com.vinsonzhan.onekey.greendao.CategoryDao;
import com.vinsonzhan.onekey.model.Account;
import com.vinsonzhan.onekey.model.Category;
import com.vinsonzhan.onekey.model.FootLine;

import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.ArrayList;
import java.util.List;

/**
 * project:onekey
 * email：zhanwjwit@163.com
 * time：1/26/18 1:28 PM
 * author：Vinson.Zhan
 * comment：
 */
public class DataUtil {

    public static ArrayList<MultiItemEntity> getData() {
        ArrayList<MultiItemEntity> list = new ArrayList<>();
        QueryBuilder<Category> categoryQb = App.getDaoSession().getCategoryDao().queryBuilder();
        WhereCondition condition1 = CategoryDao.Properties.IsDefault.eq(true);
        WhereCondition condition2 = CategoryDao.Properties.Count.gt(0);
        List<Category> categories = categoryQb.whereOr(condition1, condition2).list();
        for (int i = 0; i < categories.size(); i++) {
            Category category = categories.get(i);
            QueryBuilder<Account> accountQb = App.getDaoSession().getAccountDao().queryBuilder();
            List<Account> accounts = accountQb.where(AccountDao.Properties.Category.eq(category
                    .getName())).list();
//            KLog.d(accounts);
            if (category.getSubItems() != null) category.getSubItems().clear();
            for (int j = 0; j < accounts.size(); j++) {
                category.addSubItem(accounts.get(j));
//                KLog.d("add account :" + accounts.get(j));
            }

            if (category.getSubItems() != null && category.getSubItems().size() > 0)
                category.addSubItem(new FootLine());

            list.add(category);
        }
        return list;
    }

    public static Category getCategoryByName(String name) {
        QueryBuilder<Category> qb = App.getDaoSession().getCategoryDao().queryBuilder();
        WhereCondition condition = CategoryDao.Properties.Name.eq(name);
        return qb.where(condition).unique();
    }

    public static Account getAccountById(Long id) {
        QueryBuilder<Account> qb = App.getDaoSession().getAccountDao().queryBuilder();
        WhereCondition condition = AccountDao.Properties.Id.eq(id);
        return qb.where(condition).unique();
    }
}
