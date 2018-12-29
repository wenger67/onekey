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
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
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
import com.wei.android.lib.fingerprintidentify.aosp.FingerprintManagerCompat;

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

        if (!PreferenceUtils.hasUnlockPattern()) {
            KLog.d("unlock pattern not exist, goto create");
            ActivityUtils.startActivity(new Intent(this, CreateLockActivity.class));
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            FingerprintManagerCompat fmc = FingerprintManagerCompat.from(this);
            if (fmc.isHardwareDetected() && fmc.hasEnrolledFingerprints()) {
                fmc.authenticate(null, 0, null, authenticationCallback , null);
            } else {
                KLog.d(fmc.isHardwareDetected() + ", " +fmc.hasEnrolledFingerprints());
            }

        }
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

    private void doSuccess() {
        switch (App.getInstance().getStartMode()) {
            case StartMode.NOT_START_APP:
            case StartMode.START_COLD:
            case StartMode.START_BACK_KEY_BG:
                // normal start , launch MainActivity
                ActivityUtils.startActivity(new Intent(LoginActivity.this, MainActivity.class));
                LoginActivity.this.finish();
                break;
            case StartMode.START_HOME_KEY_BG:
                // home event backend, just resume
                finish();
                break;
        }
        App.getInstance().setStartMode(StartMode.NOT_START_APP); // restore flag
        PreferenceUtils.setFailedTimes(0); // reset failed times
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
                tipsTitle.startAnimation(getAlphaAnimation(0, 1, 500));
                tipsContent.setVisibility(View.INVISIBLE);
                lockView.setViewMode(PatternLockView.PatternViewMode.WRONG);
                handler.sendEmptyMessageDelayed(MSG_CLEAR_PATTERN, 1000);
                return;
            }

            if (PreferenceUtils.compareUnlockPattern(key)) {
                KLog.d("login success");
                lockView.clearPattern();
                doSuccess();
            } else {
                KLog.d("login failed");
                errorCount ++;
                // TODO: 2/6/18 lock app if failed more than 5 times
                tipsTitle.setText(getString(R.string.tips_fingerprint_error1_retry));
                tipsTitle.startAnimation(getAlphaAnimation(0, 1, 500));
                lockView.setViewMode(PatternLockView.PatternViewMode.WRONG);
                handler.removeMessages(MSG_CLEAR_PATTERN);
                handler.sendEmptyMessageDelayed(MSG_CLEAR_PATTERN, 1000);
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

    FingerprintManagerCompat.AuthenticationCallback authenticationCallback = new FingerprintManagerCompat.AuthenticationCallback() {
        @Override
        public void onAuthenticationError(int errMsgId, CharSequence errString) {
            super.onAuthenticationError(errMsgId, errString);
            KLog.d(errString);
            tipsTitle.setText(R.string.tips_fingerprint_error1_retry);
            tipsTitle.startAnimation(getAlphaAnimation(0, 1, 500));
        }

        @Override
        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
            super.onAuthenticationHelp(helpMsgId, helpString);
            KLog.d(helpString);
            tipsTitle.setText(helpString);
            tipsContent.setVisibility(View.INVISIBLE);
            tipsTitle.startAnimation(getAlphaAnimation(0, 1, 500));
        }

        @Override
        public void onAuthenticationSucceeded(FingerprintManagerCompat
                                                      .AuthenticationResult result) {
            super.onAuthenticationSucceeded(result);
            KLog.d();
            if (PreferenceUtils.getFailedTimes() >= 5) {
                tipsTitle.setText(R.string.title_draw_pattern);
                tipsContent.setVisibility(View.VISIBLE);
                tipsContent.setText(R.string.tips_fingerprint_error3_disable);
                tipsTitle.startAnimation(getAlphaAnimation(0, 1, 500));
                tipsContent.startAnimation(getAlphaAnimation(0, 1, 500));
                lockView.startAnimation(getAlphaAnimation(0.3f, 1, 500));
            } else {
                doSuccess();
            }
        }

        @Override
        public void onAuthenticationFailed() {
            super.onAuthenticationFailed();
            KLog.d();
            int time = PreferenceUtils.getFailedTimes();
            PreferenceUtils.setFailedTimes(++time);

            if (time <= 3) {
                tipsTitle.setText(R.string.tips_fingerprint_error1_retry);
            } else if (time < 5) {
                tipsTitle.setText(R.string.title_draw_pattern);
                tipsContent.setVisibility(View.VISIBLE);
                tipsContent.setText(R.string.tips_fingerprint_error2_wrong);
            } else {
                tipsTitle.setText(R.string.title_draw_pattern);
                tipsContent.setVisibility(View.VISIBLE);
                tipsContent.setText(R.string.tips_fingerprint_error3_disable);
            }
            tipsTitle.startAnimation(getAlphaAnimation(0, 1, 500));
            tipsContent.startAnimation(getAlphaAnimation(0, 1, 500));
            lockView.startAnimation(getAlphaAnimation(0.3f, 1, 500));
        }
    };

    private Animation getAlphaAnimation(float start, float end, long duration) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(start, end);
        alphaAnimation.setDuration(duration);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                tipsTitle.setTextColor(getResources().getColor(R.color.error));
                tipsContent.setTextColor(getResources().getColor(R.color.error));
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                tipsTitle.setTextColor(getResources().getColor(R.color.black));
                tipsContent.setTextColor(getResources().getColor(R.color.black));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        return alphaAnimation;
    }
}
