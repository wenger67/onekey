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
import android.animation.PropertyValuesHolder;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;
import android.widget.Toolbar;

import com.androidadvance.topsnackbar.TSnackbar;
import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.beardedhen.androidbootstrap.font.FontAwesome;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.SnackbarUtils;
import com.chad.library.adapter.base.animation.SlideInLeftAnimation;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.socks.library.KLog;
import com.vinsonzhan.onekey.App;
import com.vinsonzhan.onekey.R;
import com.vinsonzhan.onekey.adapter.ExpandableItemAdapter;
import com.vinsonzhan.onekey.common.CopyListener;
import com.vinsonzhan.onekey.common.OpsListener;
import com.vinsonzhan.onekey.greendao.CategoryDao;
import com.vinsonzhan.onekey.model.Account;
import com.vinsonzhan.onekey.model.Category;
import com.vinsonzhan.onekey.util.DataUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseExitActivity implements OpsListener, CopyListener {

    @BindView(R.id.rv_category) RecyclerView recyclerView;
    @BindView(R.id.navigation) NavigationView navigationView;
    @BindView(R.id.toolBar) android.support.v7.widget.Toolbar toolbar;
    @BindView(R.id.drawer) DrawerLayout drawerLayout;
    ArrayList<MultiItemEntity> list;
    ExpandableItemAdapter adapter;
    FloatingActionButton fBtn;
    FloatingActionMenu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,
                R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayShowTitleEnabled(true);
        if (getSupportActionBar() != null) getSupportActionBar().setHomeButtonEnabled(true);
        toggle.syncState();

        adapter = new ExpandableItemAdapter(new ArrayList<MultiItemEntity>());
        adapter.setListener(this);
        adapter.setCopyListener(this);
        adapter.setEnableLoadMore(true);
        adapter.setPreLoadNumber(20);
        adapter.openLoadAnimation(new SlideInLeftAnimation());

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
        setupFloatingAction();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                KLog.d(newState);
                //TODO
            }

            @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                KLog.d(dx + ", " + dy);
                if (dy < 0) {
                    if (fBtn.getVisibility() == View.INVISIBLE) {
                        // up scroll
                        if (menu.isOpen()) menu.close(true);
                        ObjectAnimator animator = ObjectAnimator.ofFloat(fBtn, "translationY",
                                200, 0);
                        animator.setDuration(300);
                        animator.setInterpolator(new LinearInterpolator());
                        animator.start();
                        fBtn.setVisibility(View.VISIBLE);
                    }
                } else if (dy > 0) {
                    if (menu.isOpen()) menu.close(true);
                    // down scroll, dismiss floating button
                    ObjectAnimator animator = ObjectAnimator.ofFloat(fBtn, "translationY", 0, 200);
                    animator.setDuration(300);
                    animator.setInterpolator(new LinearInterpolator());
                    animator.start();
                    fBtn.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private AwesomeTextView getIcon(CharSequence icon, int size, int color) {
        int s = size == 0 ? 24 : size;
        int c = color == 0 ? android.R.color.black : color;

        AwesomeTextView fab = new AwesomeTextView(this);
        fab.setFontAwesomeIcon(icon);
        fab.setTextSize(TypedValue.COMPLEX_UNIT_DIP, s);
        fab.setTextColor(getResources().getColor(c));
        fab.setClickable(true);

        return fab;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tool_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.action_search:
                this.startActivity(new Intent(MainActivity.this, SearchActivity.class));
                return true;
            case R.id.action_create:
                this.startActivity(new Intent(MainActivity.this, CreateAcountActivity.class));
                return true;
        }
        return true;
    }

    private void setupFloatingAction() {
        final AwesomeTextView fab = getIcon(FontAwesome.FA_ARROWS, 0, R.color.colorAccent);

        fBtn = new FloatingActionButton.Builder(this)
                .setContentView(fab).build();

        SubActionButton.Builder subBtn = new SubActionButton.Builder(this);
        subBtn.setLayoutParams(new FloatingActionButton.LayoutParams(SizeUtils.dp2px(48),
                SizeUtils.dp2px(48)));
        final AwesomeTextView setting = getIcon(FontAwesome.FA_LIST, 16, R.color.colorAccent);
        AwesomeTextView create = getIcon(FontAwesome.FA_PLUS, 16, R.color.colorAccent);
        final AwesomeTextView search = getIcon(FontAwesome.FA_SEARCH, 16, R.color.colorAccent);

        View settingView = subBtn.setContentView(setting).build();
        View createView = subBtn.setContentView(create).build();
        View searchView = subBtn.setContentView(search).build();

        menu = new FloatingActionMenu.Builder(this)
                .addSubActionView(createView)
                .addSubActionView(searchView)
                .addSubActionView(settingView)
                .attachTo(fBtn)
                .build();


        menu.setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
            @Override public void onMenuOpened(FloatingActionMenu floatingActionMenu) {
                fab.setRotation(0);
                PropertyValuesHolder pVH = PropertyValuesHolder.ofFloat(View.ROTATION, 45);
                ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(fab, pVH);
                animator.start();
            }

            @Override public void onMenuClosed(FloatingActionMenu floatingActionMenu) {
                fab.setRotation(45);
                PropertyValuesHolder pVH = PropertyValuesHolder.ofFloat(View.ROTATION, 0);
                ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(fab, pVH);
                animator.start();
            }
        });

        createView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                KLog.d("create");
                menu.close(true);
                ActivityUtils.startActivity(new Intent(MainActivity.this, CreateAcountActivity
                        .class));
            }
        });

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                KLog.d("search");
                menu.close(true);
                ActivityUtils.startActivity(new Intent(MainActivity.this, SearchActivity.class));
            }
        });

        settingView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                KLog.d("setting");
                menu.close(true);
                ActivityUtils.startActivity(new Intent(MainActivity.this, SettingActivity.class));
            }
        });
    }

    @Override protected void onResume() {
        super.onResume();

        new Thread(new Runnable() {
            @Override public void run() {
                long time = SystemClock.currentThreadTimeMillis();
                KLog.d("start:" + time);
                list = DataUtil.getData();
                recyclerView.post(new Runnable() {
                    @Override public void run() {
                        adapter.setNewData(list);
                    }
                });
                KLog.d("cost :" + (SystemClock.currentThreadTimeMillis() - time) + " ms");
            }
        }).start();
    }

    @Override public void onDeleteAccount(Account account, int pos) {
        KLog.d();
        // update account table
        App.getDaoSession().getAccountDao().delete(account);
        // update mCurCategory table
        String categoryName = account.getCategory();
        CategoryDao categoryDao = App.getDaoSession().getCategoryDao();
        Category category = categoryDao.queryBuilder().where(CategoryDao.Properties.Name.eq
                (categoryName)).unique();
        category.setCount(category.getCount() - 1);
        categoryDao.update(category);

        // update ui
        adapter.remove(pos);
    }

    @Override public void onModifyAccount(Account account, int pos) {
        KLog.d();
        Intent intent = new Intent(this, CreateAcountActivity.class);
        intent.putExtra(CreateAcountActivity.EXTRA_ACCOUNT, account);
        intent.putExtra(CreateAcountActivity.EXTRA_CATEGORY,
                DataUtil.getCategoryByName(account.getCategory()));
        ActivityUtils.startActivity(intent);
    }

    @Override public void onClickEmptyCategory(Category category, int pos) {
        Point point = new Point();
        getWindow().getWindowManager().getDefaultDisplay().getSize(point);
        KLog.d(point);
        SnackbarUtils.with(recyclerView)
                .setMessage(getString(R.string.category_click_tips))
                .showWarning();
    }

    @Override public void onLongClickCategory(Category category, int pos) {
        Intent intent = new Intent(MainActivity.this, CreateAcountActivity.class);
        intent.putExtra(CreateAcountActivity.EXTRA_CATEGORY, category);
        ActivityUtils.startActivity(intent);
    }

    @Override public void onAccountTitleCopied(String title) {
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setText(title);
        String msg = getResources().getString(R.string.create_title_hint) + getResources()
                .getString(R.string.copy_success);
        snackSuccess(msg);
    }

    @Override public void onAccountUsernameCopied(String userName) {
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setText(userName);
        String msg = getResources().getString(R.string.create_account) + getResources()
                .getString(R.string.copy_success);
        snackSuccess(msg);
    }

    @Override public void onAccountPasswordCopied(String password) {
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setText(password);
        String msg = getResources().getString(R.string.create_password) + getResources()
                .getString(R.string.copy_success);
        snackSuccess(msg);
    }

    private void snackSuccess(String msg) {
        TSnackbar snackbar = TSnackbar.make(recyclerView, msg, TSnackbar.LENGTH_LONG);
        View view = snackbar.getView();
        view.setBackgroundColor(getResources().getColor(R.color.message));
        TextView textView = (TextView) view.findViewById(com.androidadvance.topsnackbar.R.id
                .snackbar_text);
        textView.setTextColor(getResources().getColor(R.color.success));
        snackbar.show();
    }

    @Override public void onAccountCommentCopied(String title) {

    }
}
