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
import com.vinsonzhan.onekey.R;
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
 * time：1/18/18 3:53 PM
 * author：Vinson.Zhan
 * comment：
 */
public class CreateLockActivity extends BaseExitActivity {

    private static final int MSG_CLEAR_PATTERN = 1;
    @BindView(R.id.tips_title)
    TextView tipsTitle;
    @BindView(R.id.tips_content)
    TextView tipsContent;
    @BindView(R.id.skip)
    TextView txtSkip;
    @BindView(R.id.reset)
    TextView txtReset;
    @BindView(R.id.pattern_lock_view)
    PatternLockView lockView;
    String firstLock = "";
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
                tipsTitle.setText(R.string.tips_error_draw_less);
                lockView.setViewMode(PatternLockView.PatternViewMode.WRONG);
                handler.sendEmptyMessageDelayed(MSG_CLEAR_PATTERN, 1000);
                return;
            }

            if (firstLock.isEmpty()) {
                firstLock = key;
                tipsTitle.setText(R.string.tips_draw_again);
                lockView.setViewMode(PatternLockView.PatternViewMode.CORRECT);
                handler.sendEmptyMessageDelayed(MSG_CLEAR_PATTERN, 1000);
            } else if (firstLock.equals(key)) {
                KLog.d("match");
                PreferenceUtils.setUnlockPattern(key);
                lockView.clearPattern();
                ActivityUtils.startActivity(new Intent(CreateLockActivity.this, LoginActivity
                        .class));
                CreateLockActivity.this.finish();
            } else {
                KLog.d("not match");
                tipsTitle.setText(R.string.tip_error_draw_wrong);
                lockView.setViewMode(PatternLockView.PatternViewMode.WRONG);
                handler.sendEmptyMessageDelayed(MSG_CLEAR_PATTERN, 1000);
            }
        }

        @Override
        public void onCleared() {
            KLog.d("Pattern has been cleared");
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_create_lock_key);
        ButterKnife.bind(this);
        initView();
        initEvent();
    }

    private void initEvent() {
        txtReset.setClickable(true);
        txtReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lockView.clearPattern();
                lockView.setViewMode(PatternLockView.PatternViewMode.CORRECT);
                firstLock = "";
                tipsTitle.setText(getString(R.string.tips_draw_pattern));
            }
        });
    }

    private void initView() {
        tipsTitle.setText(R.string.tips_draw_pattern);

        lockView.setViewMode(PatternLockView.PatternViewMode.CORRECT);
        lockView.setInStealthMode(false);
        lockView.setTactileFeedbackEnabled(true);
        lockView.setInputEnabled(true);
        lockView.addPatternLockListener(mPatternLockViewListener);
    }


}
