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

package com.vinsonzhan.onekey.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand;
import com.blankj.utilcode.util.ActivityUtils;
import com.socks.library.KLog;
import com.vinsonzhan.onekey.App;
import com.vinsonzhan.onekey.R;
import com.vinsonzhan.onekey.common.StartMode;
import com.vinsonzhan.onekey.util.PreferenceUtils;
import com.vinsonzhan.onekey.widget.patternlock.PatternLockUtils;
import com.vinsonzhan.onekey.widget.patternlock.PatternLockView;
import com.vinsonzhan.onekey.widget.patternlock.PatternLockViewListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * project:onekey
 * email：zhanwjwit@163.com
 * time：1/18/18 6:26 PM
 * author：Vinson.Zhan
 * comment：
 */
public class LoginActivity extends BaseExitActivity {

    @BindView(R.id.profile_image) BootstrapCircleThumbnail image;
    @BindView(R.id.profile_name) AwesomeTextView name;
    @BindView(R.id.tips)
    TextView tips;
    @BindView(R.id.pattern_lock_view)
    PatternLockView lockView;

    int errorCount = 0;
    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_create_lock_key);
        ButterKnife.bind(this);
        initView();
    }

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

            if (pattern.size() < 4) {
                KLog.d("lock key show large that 4 dot!");
                // draw again
                lockView.clearPattern();
                tips.setText(R.string.tips_invalid_redraw);
                tips.setTextColor(getResources().getColor(R.color.warning));
                return;
            }

            if (PreferenceUtils.compareLockKey(LoginActivity.this, key)) {
                KLog.d("login success");
                // jump
                lockView.clearPattern();
                tips.setText(R.string.tips_draw_key_correct);
                tips.setTextColor(getResources().getColor(R.color.success));
                tips.setVisibility(View.VISIBLE);
                switch (App.getInstance().getStartMode()) {
                    case StartMode.START_NORMAL:
                    case StartMode.START_BACK_KEY_BG:
                        ActivityUtils.startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        LoginActivity.this.finish();
                        break;
                    case StartMode.START_HOME_KEY_BG:
                        KLog.d(ActivityUtils.getTopActivity().getLocalClassName());
                        LoginActivity.this.finish();
                        break;
                }
                App.getInstance().setStartMode(StartMode.START_NORMAL); // restore flag
            } else {
                KLog.d("login failed");
                errorCount ++;
                lockView.clearPattern();
                // TODO: 2/6/18 lock app if failed more than 5 times
                tips.setText(getString(R.string.tips_draw_key_wrong));
                tips.setVisibility(View.VISIBLE);
                if (errorCount == 2) {
                    tips.setTextColor(getResources().getColor(R.color.warning));
                } else if (errorCount >= 3)
                    tips.setTextColor(getResources().getColor(R.color.error));

            }

        }

        @Override
        public void onCleared() {
            KLog.d("Pattern has been cleared");
        }
    };

    private void initView() {
        tips.setText(R.string.tips_start_draw_key);
        tips.setTextColor(getResources().getColor(R.color.colorPrimary));

        lockView.setViewMode(PatternLockView.PatternViewMode.CORRECT);
        lockView.setInStealthMode(false);
        lockView.setTactileFeedbackEnabled(true);
        lockView.setInputEnabled(true);
        lockView.addPatternLockListener(mPatternLockViewListener);
    }
}
