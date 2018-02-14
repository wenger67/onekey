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
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapText;
import com.beardedhen.androidbootstrap.font.FontAwesome;
import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.socks.library.KLog;
import com.vinsonzhan.onekey.App;
import com.vinsonzhan.onekey.R;
import com.vinsonzhan.onekey.common.CopyListener;
import com.vinsonzhan.onekey.common.DataChangeListener;
import com.vinsonzhan.onekey.common.OpsListener;
import com.vinsonzhan.onekey.greendao.CategoryDao;
import com.vinsonzhan.onekey.model.Account;
import com.vinsonzhan.onekey.model.Category;
import com.vinsonzhan.onekey.swipewithcallback.EasySwipeMenuLayout;
import com.vinsonzhan.onekey.swipewithcallback.State;
import com.vinsonzhan.onekey.swipewithcallback.SwipeStateChangeListener;

import java.util.List;

/**
 * project:onekey
 * email：zhanwjwit@163.com
 * time：1/22/18 4:45 PM
 * author：Vinson.Zhan
 * comment：
 */
public class ExpandableItemAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity,
        BaseViewHolder> {

    public static final int TYPE_CATEGORY = 0;
    public static final int TYPE_ACCOUNT = 1;
    public static final int TYPE_ACCOUNT_FOOT = 2;

    public ExpandableItemAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_CATEGORY, R.layout.item_category);
        addItemType(TYPE_ACCOUNT, R.layout.item_account_swipable);
        addItemType(TYPE_ACCOUNT_FOOT, R.layout.item_account_foot);
    }

    private OpsListener listener;
    private CopyListener copyListener;

    public void setCopyListener(CopyListener copyListener) {
        this.copyListener = copyListener;
    }

    public void setListener(OpsListener listener) {
        this.listener = listener;
    }

    @Override protected void convert(final BaseViewHolder helper, final MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case TYPE_CATEGORY:
                final Category category = (Category) item;
                AwesomeTextView icon = helper.getView(R.id.category_icon);
                icon.setBootstrapText(new BootstrapText.Builder(App.getInstance())
                        .addFontAwesomeIcon(category.getIcon()).build());

                TextView name = helper.getView(R.id.category_name);
                name.setText(category.getName());

                final TextView countTv = helper.getView(R.id.category_count);
                countTv.setTextColor(category.getCount() > 0 ? App.getRes().getColor(R.color
                        .colorPrimaryDark) : App.getRes().getColor(R.color.bg));
                countTv.setText(String.valueOf(category.getCount()));

                category.setDataChangeListener(new DataChangeListener() {
                    @Override public void onDataChange(int count) {
                        countTv.setText(String.valueOf(count));
                    }
                });

                View.OnClickListener cl = new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        if (category.getCount() == 0 && listener != null)
                            listener.onClickEmptyCategory(category, helper.getAdapterPosition());
                        else {
                            int pos = helper.getAdapterPosition();
                            if (((Category) item).isExpanded())
                                collapse(pos, true);
                            else expand(pos, true);
                        }
                    }
                };

                View.OnLongClickListener lcl = new View.OnLongClickListener() {
                    @Override public boolean onLongClick(View v) {
                        if (listener != null) {
                            listener.onLongClickCategory(category, helper.getAdapterPosition());
                            return true;
                        }
                        return false;
                    }
                };

                icon.setOnLongClickListener(lcl);
                name.setOnLongClickListener(lcl);
                countTv.setOnLongClickListener(lcl);
                helper.itemView.setOnLongClickListener(lcl);

                icon.setOnClickListener(cl);
                name.setOnClickListener(cl);
                countTv.setOnClickListener(cl);
                helper.itemView.setOnClickListener(cl);
                break;
            case TYPE_ACCOUNT:
                final Account account = (Account) item;
                final TextView titleTv = helper.getView(R.id.account_title_tv);
                titleTv.setText(account.getTitle());
                TextView usernameTv = helper.getView(R.id.account_username_tv);
                usernameTv.setText(account.getUserName());
                TextView passwordTv = helper.getView(R.id.account_password_tv);
                passwordTv.setText(account.getPassword());

                setupCopyListener(titleTv);
                setupCopyListener(usernameTv);
                setupCopyListener(passwordTv);

                AwesomeTextView awesomeTextView = helper.getView(R.id.account_username_icon);
                awesomeTextView.setBootstrapText(new BootstrapText.Builder(App.getInstance())
                        .addFontAwesomeIcon(FontAwesome.FA_USER_CIRCLE).build());

                final BootstrapButton del = helper.getView(R.id.account_del);
                final BootstrapButton mod = helper.getView(R.id.account_mod);

                final AnimatorSet set = getAnimatorSet(helper, account);

                del.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        set.start();
                    }
                });

                mod.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        if (listener != null)
                            listener.onModifyAccount(account, helper.getAdapterPosition());
                    }
                });



                break;
        }
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

    private AnimatorSet getAnimatorSet(final BaseViewHolder helper, final Account account) {

        final BootstrapButton del = helper.getView(R.id.account_del);
        final BootstrapButton mod = helper.getView(R.id.account_mod);

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
                        remove(helper.getAdapterPosition()); // ui update
                        //TODO database update
                        if (listener != null)
                            listener.onDeleteAccount(account, helper.getAdapterPosition());
                    }
                });

                EasySwipeMenuLayout accountItemView = helper.getView(R.id.account_item);

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
}
