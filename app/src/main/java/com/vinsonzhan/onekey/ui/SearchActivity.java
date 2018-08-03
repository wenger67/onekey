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

import android.animation.ObjectAnimator;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.beardedhen.androidbootstrap.font.FontAwesome;
import com.blankj.utilcode.util.ActivityUtils;
import com.socks.library.KLog;
import com.vinsonzhan.onekey.App;
import com.vinsonzhan.onekey.R;
import com.vinsonzhan.onekey.adapter.SearchResultAdapter;
import com.vinsonzhan.onekey.common.CopyListener;
import com.vinsonzhan.onekey.common.OpsListener;
import com.vinsonzhan.onekey.greendao.AccountDao;
import com.vinsonzhan.onekey.greendao.CategoryDao;
import com.vinsonzhan.onekey.model.Account;
import com.vinsonzhan.onekey.model.Category;
import com.vinsonzhan.onekey.util.DataUtil;

import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.davidea.flexibleadapter.items.IFlexible;

/**
 * project:onekey
 * email：zhanwjwit@163.com
 * time：1/24/18 7:14 PM
 * author：Vinson.Zhan
 * comment：
 */
public class SearchActivity extends BaseActivity implements OpsListener {

    @BindView(R.id.search_view) SearchView searchView;
    @BindView(R.id.search_result) ListView searchResult;

    SearchResultAdapter adapter;
    String curStr;
    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.search));
        setIcon(FontAwesome.FA_ARROW_LEFT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupActionBar();

        ButterKnife.bind(this);

        adapter = new SearchResultAdapter(this, null);
        adapter.setListener(this);
        initView();
        searchResult.setAdapter(adapter);
    }

    private void initView() {
        searchView.onActionViewExpanded();
        searchView.setIconifiedByDefault(true);
        searchView.setIconified(false);
        searchView.setFocusable(true);
        searchView.requestFocus();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    adapter.swapCursor(null);
                    curStr = "";
                    return false;
                }
                curStr = newText;
                adapter.swapCursor(getCursor(newText));
                return true;
            }
        });

        searchResult.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context
                            .INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    private Cursor getCursor(String str) {
        QueryBuilder<Account> qb = App.getDaoSession().getAccountDao().queryBuilder();
        WhereCondition condition1 = AccountDao.Properties.Title.like("%" + str + "%");
        WhereCondition condition2 = AccountDao.Properties.UserName.like("%" + str + "%");
        WhereCondition condition3 = AccountDao.Properties.Comment.like("%" + str + "%");
        return qb.whereOr(condition1, condition2, condition3).buildCursor().query();

    }

//    @Override public void onDeleteAccount(Account account, int pos) {
//        KLog.d();
//        // update account table
//        App.getDaoSession().getAccountDao().delete(account);
//        // update mCurCategory table
//        String categoryName = account.getCategory();
//        CategoryDao categoryDao = App.getDaoSession().getCategoryDao();
//        Category category = categoryDao.queryBuilder().where(CategoryDao.Properties.Name.eq
//                (categoryName)).unique();
//        category.setCount(category.getCount() - 1);
//        categoryDao.update(category);
//        // update ui
//
//        View view = searchResult.getChildAt(pos);
//        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "scaleX", 1, 0);
//        objectAnimator.setDuration(300);
//        objectAnimator.start();
//
//        adapter.swapCursor(getCursor(curStr));
//    }

//    @Override public void onModifyAccount(Account account, int pos) {
//        KLog.d();
//        Intent intent = new Intent(this, CreateAcountActivity.class);
//        intent.putExtra(CreateAcountActivity.EXTRA_ACCOUNT, account);
//        intent.putExtra(CreateAcountActivity.EXTRA_CATEGORY,
//                DataUtil.getCategoryByName(account.getCategory()));
//        ActivityUtils.startActivity(intent);
//
//        // handle result
//    }

    @Override
    public void onOpsEvent(IFlexible data, int opsType) {

    }


//    @Override public void onAccountTitleCopied(String title) {
//        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
//        cm.setText(title);
//        String msg = getResources().getString(R.string.create_title_hint) + getResources()
//                .getString(R.string.copy_success);
//        snackSuccess(msg);
//    }
//
//    @Override public void onAccountUsernameCopied(String username) {
//        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
//        cm.setText(username);
//        String msg = getResources().getString(R.string.create_account) + getResources()
//                .getString(R.string.copy_success);
//        snackSuccess(msg);
//    }
//
//    @Override public void onAccountPasswordCopied(String password) {
//        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
//        cm.setText(password);
//        String msg = getResources().getString(R.string.create_password) + getResources()
//                .getString(R.string.copy_success);
//        snackSuccess(msg);
//    }
//
//    @Override public void onAccountCommentCopied(String title) {
//
//    }

    private void snackSuccess(String msg) {
        TSnackbar snackbar = TSnackbar.make(searchResult, msg, TSnackbar.LENGTH_LONG);
        View view = snackbar.getView();
        view.setBackgroundColor(getResources().getColor(R.color.message));
        TextView textView = (TextView) view.findViewById(com.androidadvance.topsnackbar.R.id
                .snackbar_text);
        textView.setTextColor(getResources().getColor(R.color.success));
        snackbar.show();
    }
}
