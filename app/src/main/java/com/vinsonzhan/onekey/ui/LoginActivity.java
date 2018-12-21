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
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

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
    @BindView(R.id.title) TextView title;
    @BindView(R.id.tips_title) TextView tipsTitle;
    @BindView(R.id.tips_content) TextView tipsContent;
    @BindView(R.id.skip) TextView skip;
    @BindView(R.id.reset) TextView reset;

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
    private static final int MSG_CLEAR_PATTERN = 1;

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_CLEAR_PATTERN:
                    lockView.clearPattern();
                    break;
            }
        }
    };

    private PatternLockViewListener mPatternLockViewListener = new PatternLockViewListener() {
        @Override
        public void onStarted() {
            KLog.d("Pattern drawing started");
        }

        @Override
        public void onProgress(List<PatternLockView.Dot> progressPattern) {
            KLog.d("Pattern progress: " +
                    PatternLockUtils.patternToString(lockView, progressPattern));
            tipsTitle.setText(getString(R.string.tips_draw_down));
        }

        @Override
        public void onComplete(List<PatternLockView.Dot> pattern) {
            String key = PatternLockUtils.patternToString(lockView, pattern);
            KLog.d("Pattern complete: " + key);

            if (pattern.size() < 4) {
                KLog.d("lock key show large that 4 dot!");
                // draw again
                tipsTitle.setText(R.string.tips_error_draw_less);
                tipsContent.setVisibility(View.INVISIBLE);
                lockView.setViewMode(PatternLockView.PatternViewMode.WRONG);
                handler.sendEmptyMessageDelayed(MSG_CLEAR_PATTERN, 1000);
                return;
            }

            if (PreferenceUtils.compareUnlockPattern(key)) {
                KLog.d("login success");
                lockView.clearPattern();
                switch (App.getInstance().getStartMode()) {
                    case StartMode.START_NORMAL:
                    case StartMode.START_BACK_KEY_BG:
                        // normal start , launch MainActivity
                        ActivityUtils.startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        LoginActivity.this.finish();
                        break;
                    case StartMode.START_HOME_KEY_BG:
                        // home event backend, just resume
                        KLog.d(ActivityUtils.getTopActivity().getLocalClassName());
                        LoginActivity.this.finish();
                        break;
                }
                App.getInstance().setStartMode(StartMode.START_NORMAL); // restore flag
            } else {
                KLog.d("login failed");
                errorCount ++;
                // TODO: 2/6/18 lock app if failed more than 5 times
                if (errorCount < 3) {
                    tipsTitle.setText(getString(R.string.tips_fingerprint_error1_retry));
                    lockView.setViewMode(PatternLockView.PatternViewMode.WRONG);
                    handler.removeMessages(MSG_CLEAR_PATTERN);
                    handler.sendEmptyMessageDelayed(MSG_CLEAR_PATTERN, 1000);
                } else if (errorCount < 5) {
                    tipsTitle.setText(getString(R.string.title_draw_pattern));
                    tipsContent.setVisibility(View.VISIBLE);
                    tipsContent.setText(getString(R.string.tips_fingerprint_error2_wrong));
                    lockView.setViewMode(PatternLockView.PatternViewMode.WRONG);
                    handler.removeMessages(MSG_CLEAR_PATTERN);
                    handler.sendEmptyMessageDelayed(MSG_CLEAR_PATTERN, 1000);
                } else {
                    tipsTitle.setText(getString(R.string.title_draw_pattern));
                    tipsContent.setVisibility(View.VISIBLE);
                    tipsContent.setText(getString(R.string.tips_fingerprint_error3_disable));
                    lockView.setViewMode(PatternLockView.PatternViewMode.WRONG);
                    handler.removeMessages(MSG_CLEAR_PATTERN);
                    handler.sendEmptyMessageDelayed(MSG_CLEAR_PATTERN, 1000);
                }
            }
        }

        @Override
        public void onCleared() {
            KLog.d("Pattern has been cleared");
        }
    };

    private void initView() {
        tipsTitle.setText(R.string.tips_login);
        tipsContent.setVisibility(View.INVISIBLE);
        title.setVisibility(View.GONE);
        skip.setVisibility(View.GONE);
        reset.setVisibility(View.GONE);

        lockView.setViewMode(PatternLockView.PatternViewMode.CORRECT);
        lockView.setInStealthMode(false);
        lockView.setTactileFeedbackEnabled(true);
        lockView.setInputEnabled(true);
        lockView.addPatternLockListener(mPatternLockViewListener);
    }
}
