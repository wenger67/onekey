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

package com.vinsonzhan.onekey.adapter;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapText;
import com.beardedhen.androidbootstrap.font.FontAwesome;
import com.blankj.utilcode.util.SizeUtils;
import com.socks.library.KLog;
import com.vinsonzhan.onekey.App;
import com.vinsonzhan.onekey.R;
import com.vinsonzhan.onekey.common.CopyListener;
import com.vinsonzhan.onekey.common.OpsListener;
import com.vinsonzhan.onekey.greendao.AccountDao;
import com.vinsonzhan.onekey.model.Account;
import com.vinsonzhan.onekey.swipewithcallback.EasySwipeMenuLayout;
import com.vinsonzhan.onekey.swipewithcallback.State;
import com.vinsonzhan.onekey.swipewithcallback.SwipeStateChangeListener;
import com.vinsonzhan.onekey.util.DataUtil;

/**
 * project:onekey
 * email：zhanwjwit@163.com
 * time：2/2/18 10:48 AM
 * author：Vinson.Zhan
 * comment：
 */
public class SearchResultAdapter extends CursorAdapter {

    public SearchResultAdapter(Context context, Cursor c) {
        super(context, c);
    }

    OpsListener listener;
    CopyListener copyListener;

    public void setCopyListener(CopyListener copyListener) {
        this.copyListener = copyListener;
    }

    public void setListener(OpsListener listener) {
        this.listener = listener;
    }

    @Override public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context
                .LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_account_swipable, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    @Override public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        Long id = cursor.getLong(cursor.getColumnIndex(AccountDao.Properties.Id.columnName));
        final Account account = DataUtil.getAccountById(id);
        viewHolder.title.setText(account.getTitle());
        viewHolder.userName.setText(account.getUserName());
        viewHolder.password.setText(account.getPassword());

        setupCopyListener(viewHolder.title);
        setupCopyListener(viewHolder.userName);
        setupCopyListener(viewHolder.password);

        viewHolder.userNameIcon.setBootstrapText(new BootstrapText.Builder(App.getInstance())
                .addFontAwesomeIcon(FontAwesome.FA_USER_CIRCLE).build());

        final AnimatorSet set = getAnimatorSet(viewHolder, account);
        viewHolder.del.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                set.start();
            }
        });

        viewHolder.mod.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (listener != null)
                    listener.onModifyAccount(account, -1);
            }
        });
    }

    private AnimatorSet getAnimatorSet(final ViewHolder viewHolder, final Account account) {

        final BootstrapButton del = viewHolder.del;
        final BootstrapButton mod = viewHolder.mod;

        int from = SizeUtils.dp2px(80);
        int to = SizeUtils.dp2px(160);

        final ValueAnimator smallAnim = ValueAnimator.ofInt(from, 0);
        smallAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override public void onAnimationUpdate(ValueAnimator animation) {
                ViewGroup.LayoutParams lp = mod.getLayoutParams();
                lp.width = (int) animation.getAnimatedValue();
                mod.setLayoutParams(lp);
            }
        });

        final ValueAnimator biggerAnim = ValueAnimator.ofInt(from, to);
        biggerAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override public void onAnimationUpdate(ValueAnimator animation) {
                ViewGroup.LayoutParams lp = del.getLayoutParams();
                lp.width = (int) animation.getAnimatedValue();
                del.setLayoutParams(lp);
            }
        });

        final AnimatorSet set = new AnimatorSet();
        set.playTogether(smallAnim, biggerAnim);
        set.setDuration(300);
        set.setInterpolator(new LinearOutSlowInInterpolator());

        set.addListener(new Animator.AnimatorListener() {
            @Override public void onAnimationStart(Animator animation) {

            }

            @Override public void onAnimationEnd(Animator animation) {
                del.setText(App.getRes().getString(R.string.account_del_confirm));
                del.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        KLog.d("click delete confirm");
                        if (listener != null)
                            listener.onDeleteAccount(account, -1);
                    }
                });

                EasySwipeMenuLayout accountItemView = (EasySwipeMenuLayout) viewHolder.root;

                accountItemView.setListener(new SwipeStateChangeListener() {
                    @Override public void onStateChange(State newState, State oldState) {
                        KLog.d("new: " + newState + ", old: " + oldState);
                        if (newState == State.CLOSE && oldState == State.RIGHTOPEN) {
                            biggerAnim.reverse();
                            smallAnim.reverse();
                            del.setText(App.getRes().getString(R.string.account_del));
                        }
                    }
                });
            }

            @Override public void onAnimationCancel(Animator animation) {

            }

            @Override public void onAnimationRepeat(Animator animation) {

            }
        });

        return set;
    }

    private void setupCopyListener(final TextView textView) {
        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override public boolean onLongClick(View v) {
                KLog.d(textView.getText().toString());
                if (copyListener != null) {
                    switch (textView.getId()) {
                        case R.id.account_title_tv:
                            copyListener.onAccountTitleCopied(textView.getText().toString());
                            break;
                        case R.id.account_username_tv:
                            copyListener.onAccountUsernameCopied(textView.getText().toString());
                            break;
                        case R.id.account_password_tv:
                            copyListener.onAccountPasswordCopied(textView.getText().toString());
                            break;
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private class ViewHolder {
        View root;
        BootstrapButton del;
        BootstrapButton mod;
//        AwesomeTextView titleIcon;
        TextView title;
        AwesomeTextView userNameIcon;
        TextView userName;
//        AwesomeTextView passwordIcon;
        TextView password;
//        TextView comment;

        public ViewHolder(View view) {
            this.root = view;
            this.del = view.findViewById(R.id.account_del);
            this.mod = view.findViewById(R.id.account_mod);
//            this.titleIcon = view.findViewById(R.id.account_title_icon);
            this.title = view.findViewById(R.id.account_title_tv);
            this.userNameIcon = view.findViewById(R.id.account_username_icon);
            this.userName = view.findViewById(R.id.account_username_tv);
//            this.passwordIcon = view.findViewById(R.id.account_password_icon);
            this.password = view.findViewById(R.id.account_password_tv);
        }
    }
}
