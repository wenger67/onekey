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
import android.os.Bundle;

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
import com.vinsonzhan.onekey.util.DataUtil;
import com.vinsonzhan.onekey.util.MockDataUtil;
import com.vinsonzhan.onekey.util.PreferenceUtils;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import static com.vinsonzhan.onekey.common.StartMode.START_NORMAL;

/**
  * project:Onekey
  * email: zhanwit@163.com
  * time: 2018/12/21 14:06
  * author: Vinson. Zhan
  * comment:
  */
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

        KLog.init(BuildConfig.DEBUG);
        initDatabase();
        if (BuildConfig.DEBUG && !PreferenceUtils.hasMockData())
            initMockData();

        initListener();
        // use FontAwesome
        TypefaceProvider.registerDefaultIconSets();
        Utils.init(this);

        startActivityDispatch();
    }

    private void initMockData(){
        MockDataUtil.insertFackAccount();
        PreferenceUtils.setHasMockData(true);
    }

    private void initListener() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override public void onActivityStarted(Activity activity) {
                // when launch app from background resume, should check unlock pattern
                if (activityCount == 0 && startMode != StartMode.START_NORMAL) {
                    startActivityDispatch();
                }
                // increase count
                activityCount++;
            }

            @Override public void onActivityResumed(Activity activity) {

            }

            @Override public void onActivityPaused(Activity activity) {

            }

            @Override public void onActivityStopped(Activity activity) {
                // reduce count
                activityCount--;
            }

            @Override public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    private void initDatabase() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, DataUtil.DB_NAME);
        SQLiteDatabase writableDatabase = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(writableDatabase);
        daoSession = daoMaster.newSession();

        if (!PreferenceUtils.getDbInitialed()) {
            Object[] keys = Constants.CATAGORY_MAP.keySet().toArray();
            for (int i = 0; i <keys.length; i++) {
                String key = (String) keys[i];
                boolean flag = i < DataUtil.MAX_DEFAULT_CATEGORY_COUNT;
                Category cate = new Category((long) i, Constants.CATAGORY_MAP.get(key), key, flag, 0);
                daoSession.insert(cate);
            }
            PreferenceUtils.setDbInitialed(true);
        }

        QueryBuilder<Category> qb = daoSession.getCategoryDao().queryBuilder();
        List<Category> list = qb.list();
        KLog.d(list.toString());
    }

    private void startActivityDispatch() {
        if (PreferenceUtils.hasUnlockPattern()) {
            KLog.d("unlock pattern exist, goto login");
            ActivityUtils.startActivity(new Intent(getInstance(), LoginActivity.class));
        } else {
            KLog.d("unlock pattern not exist, goto create");
            ActivityUtils.startActivity(new Intent(this, CreateLockActivity.class));
        }
    }

}
