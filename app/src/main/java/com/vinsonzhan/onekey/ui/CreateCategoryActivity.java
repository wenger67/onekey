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

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.GridView;

import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapText;
import com.beardedhen.androidbootstrap.api.attributes.BootstrapBrand;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand;
import com.beardedhen.androidbootstrap.font.FontAwesome;
import com.socks.library.KLog;
import com.vinsonzhan.onekey.App;
import com.vinsonzhan.onekey.R;
import com.vinsonzhan.onekey.adapter.IconGridAdapter;
import com.vinsonzhan.onekey.model.Category;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * project:onekey
 * email：zhanwjwit@163.com
 * time：2/2/18 2:57 PM
 * author：Vinson.Zhan
 * comment：
 */
public class CreateCategoryActivity extends BaseActivity implements IconGridAdapter.Listener {

    @BindView(R.id.icon) AwesomeTextView icon;
    @BindView(R.id.name) EditText name;
    @BindView(R.id.icon_grid) GridView gridView;
    @BindView(R.id.save) BootstrapButton save;

    String nameStr = "";
    String iconStr;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.category));
        setIcon(FontAwesome.FA_ARROW_LEFT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_category);
        setupActionBar();

        ButterKnife.bind(this);

        IconGridAdapter adapter = new IconGridAdapter(this);
        adapter.setListener(this);
        gridView.setAdapter(adapter);
        initView();
    }

    @OnClick(R.id.save)
    void onClick(View v) {
        KLog.d();
        Category category = new Category(null, nameStr, iconStr, true, 0);
        App.getDaoSession().getCategoryDao().insert(category);
        finish();
    }

    private void initView() {
        CharSequence first = new FontAwesome().iconCodeForAttrIndex(0);
        iconStr = first.toString();
        icon.setBootstrapText(new BootstrapText.Builder(this).addFontAwesomeIcon(first).build());
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override public void afterTextChanged(Editable s) {
                nameStr = s.toString();
                if (nameStr.isEmpty())
                    save.setBootstrapBrand(DefaultBootstrapBrand.WARNING);
                else save.setBootstrapBrand(DefaultBootstrapBrand.SUCCESS);
            }
        });

        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService
                            (Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    @Override public void onItemClick(View view, String iconStr) {
        KLog.d(iconStr);
        this.iconStr = iconStr;
        this.icon.setBootstrapText(new BootstrapText.Builder(CreateCategoryActivity.this)
                .addFontAwesomeIcon(this.iconStr).build());
    }

    @Override protected void onDestroy() {
        super.onDestroy();
    }


}
