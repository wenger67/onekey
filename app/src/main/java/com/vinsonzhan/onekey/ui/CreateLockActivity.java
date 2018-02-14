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

package com.vinsonzhan.onekey.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.beardedhen.androidbootstrap.BootstrapText;
import com.blankj.utilcode.util.ActivityUtils;
import com.socks.library.KLog;
import com.vinsonzhan.onekey.R;
import com.vinsonzhan.onekey.util.PreferenceUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * project:onekey
 * email：zhanwjwit@163.com
 * time：1/18/18 3:53 PM
 * author：Vinson.Zhan
 * comment：
 */
public class CreateLockActivity extends BaseExitActivity {

    @BindView(R.id.profile_image) BootstrapCircleThumbnail image;
    @BindView(R.id.profile_name) AwesomeTextView name;
    @BindView(R.id.tips) AwesomeTextView tips;

    @BindView(R.id.pattern_lock_view) PatternLockView lockView;
    
    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_create_lock_key);
        ButterKnife.bind(this);
        initView();
    }

    String firstLock = "";

    private PatternLockViewListener mPatternLockViewListener = new PatternLockViewListener() {
        @Override
        public void onStarted() {
            KLog.d("Pattern drawing started");
        }

        @Override
        public void onProgress(List<PatternLockView.Dot> progressPattern) {
            KLog.d("Pattern progress: " +
                    PatternLockUtils.patternToString(lockView, progressPattern));
        }

        @Override
        public void onComplete(List<PatternLockView.Dot> pattern) {
            String key = PatternLockUtils.patternToString(lockView, pattern);
            KLog.d("Pattern complete: " + key);
            if (firstLock.isEmpty()) {
                firstLock = key;
                // draw again
                lockView.clearPattern();
                tips.setBootstrapText(new BootstrapText.Builder(CreateLockActivity.this).addText(getString(R.string
                        .draw_secret_again)).build());
            } else if (firstLock.equals(key)) {
                KLog.d("Set lock success");
                PreferenceUtils.saveLockKey(CreateLockActivity.this, key);
                // jump
                lockView.clearPattern();
                tips.setBootstrapText(new BootstrapText.Builder(CreateLockActivity.this).addText(getString(R.string
                        .go_to_login)).build());
                ActivityUtils.startActivity(new Intent(CreateLockActivity.this, LoginActivity.class));
                CreateLockActivity.this.finish();
            } else {
                KLog.d("Set lock failed");
                // draw again
                lockView.clearPattern();
                tips.setBootstrapText(new BootstrapText.Builder(CreateLockActivity.this).addText(getString(R.string
                        .draw_secret_again)).build());
                firstLock = "";
            }

        }

        @Override
        public void onCleared() {
            KLog.d("Pattern has been cleared");
        }
    };

    private void initView() {
        tips.setBootstrapText(new BootstrapText.Builder(this).addText(getString(R.string
                .draw_secret)).build());
        tips.setVisibility(View.VISIBLE);

        lockView.setViewMode(PatternLockView.PatternViewMode.CORRECT);
        lockView.setInStealthMode(false);
        lockView.setTactileFeedbackEnabled(true);
        lockView.setInputEnabled(true);
        lockView.addPatternLockListener(mPatternLockViewListener);
    }


}
