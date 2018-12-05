/*
 *  ******************************* Copyright (c)*********************************\
 *  **
 *  ** (c) Copyright 2018,VinsonZhan, china, wuhan
 *  ** All Rights Reserved
 *  **
 *  ** By( The OneKey Project)
 *  **
 *  **-----------------------------------版本信息------------------------------------
 *  ** 版 本: V0.1
 *  **------------------------------------------------------------------------------
 *  ********************************End of Head************************************\
 *
 */

package com.vinsonzhan.onekey;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.Utils;
import com.socks.library.KLog;
import com.vinsonzhan.onekey.common.StartMode;
import com.vinsonzhan.onekey.greendao.DaoMaster;
import com.vinsonzhan.onekey.greendao.DaoSession;
import com.vinsonzhan.onekey.model.Category;
import com.vinsonzhan.onekey.ui.CreateLockActivity;
import com.vinsonzhan.onekey.ui.LoginActivity;
import com.vinsonzhan.onekey.util.Constants;
import com.vinsonzhan.onekey.util.MockDataUtil;
import com.vinsonzhan.onekey.util.PreferenceUtils;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import static com.vinsonzhan.onekey.common.StartMode.START_NORMAL;


public class App extends Application {

    private static App INSTANCE;
    private static DaoSession daoSession;
    int activityCount = 0;
    @StartMode int startMode = START_NORMAL;

    public static App getInstance() {
        return INSTANCE;
    }

    public static Resources getRes() {
        return getInstance().getResources();
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }

    public int getStartMode() {
        return startMode;
    }

    public void setStartMode(int startMode) {
        this.startMode = startMode;
    }

    @Override public void onCreate() {
        super.onCreate();
        INSTANCE = this;

        if ("debug".equals(BuildConfig.BUILD_TYPE))
            KLog.init(true);
        else KLog.init(false);

        initDatabase();
        initListener();
        // use FontAwesome
        TypefaceProvider.registerDefaultIconSets();
        Utils.init(this);
//        PreferenceUtils.clearLockKey(this);
        startActivityDispatch();
    }

    private void initListener() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override public void onActivityStarted(Activity activity) {
                if (activityCount == 0 && startMode != StartMode.START_NORMAL) {
                    KLog.d("start up");
                    activityCount++;
                    startActivityDispatch();
                } else
                    activityCount++;
            }

            @Override public void onActivityResumed(Activity activity) {

            }

            @Override public void onActivityPaused(Activity activity) {

            }

            @Override public void onActivityStopped(Activity activity) {
                activityCount--;
            }

            @Override public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    private void initDatabase() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "onekey.db");
        SQLiteDatabase writableDatabase = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(writableDatabase);
        daoSession = daoMaster.newSession();

        if (!PreferenceUtils.isDbInitialed(this)) {
            Object[] keys = Constants.CATAGORY_MAP.keySet().toArray();
            for (int i = 0; i <keys.length; i++) {
                String key = (String) keys[i];
                boolean flag = i < 10;
                Category cate = new Category((long) i, Constants.CATAGORY_MAP.get(key), key, flag, 0);
                daoSession.insert(cate);
            }
            PreferenceUtils.saveDbInitialed(this);
        } else if (getCategoryCount() == 0){
            KLog.d("Category table is empty, fill some data");
            Object[] keys = Constants.CATAGORY_MAP.keySet().toArray();
            for (int i = 0; i <keys.length; i++) {
                String key = (String) keys[i];
                boolean flag = i < 10;
                Category cate = new Category((long) i, Constants.CATAGORY_MAP.get(key), key, flag, 0);
                daoSession.insert(cate);
            }
        }


        if (!PreferenceUtils.hasMockData(this)) {
            MockDataUtil.insertFackAccount();
            PreferenceUtils.saveMockDataFlag(this);
        }


        QueryBuilder<Category> qb = daoSession.getCategoryDao().queryBuilder();
        List<Category> list = qb.list();
        KLog.d(list.toString());
    }

    private int getCategoryCount() {
        return daoSession.getCategoryDao().queryBuilder().list().size();
    }

    private void startActivityDispatch() {
        if (PreferenceUtils.isLockExist(this)) {
            KLog.d("lock exist, goto login");
            Intent intent = new Intent(getInstance(), LoginActivity.class);
            ActivityUtils.startActivity(intent);
        } else {
            KLog.d("lock not exist, goto create");
            ActivityUtils.startActivity(new Intent(this, CreateLockActivity.class));
        }
    }

}
