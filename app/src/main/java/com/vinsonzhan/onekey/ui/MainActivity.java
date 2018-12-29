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
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SnackbarUtils;
import com.socks.library.KLog;
import com.vinsonzhan.onekey.R;
import com.vinsonzhan.onekey.adapter.CategoryAdapter;
import com.vinsonzhan.onekey.adapter.OpsType;
import com.vinsonzhan.onekey.common.OpsListener;
import com.vinsonzhan.onekey.model.Category;
import com.vinsonzhan.onekey.util.DataUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.IFlexible;

public class MainActivity extends BaseExitActivity  {

    @BindView(R.id.navigation)
    NavigationView navigationView;
    @BindView(R.id.toolBar)
    android.support.v7.widget.Toolbar toolbar;
    @BindView(R.id.drawer)
    DrawerLayout drawerLayout;
    CategoriesFragment categoriesFragment = new CategoriesFragment();
    AccountsFragment accountsFragment = new AccountsFragment();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayShowTitleEnabled(true);
        if (getSupportActionBar() != null) getSupportActionBar().setHomeButtonEnabled(true);
        toggle.syncState();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content,
                categoriesFragment).commit();
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
                this.startActivity(new Intent(this, SearchActivity.class));
                return true;
            case R.id.action_create:
                this.startActivity(new Intent(this, CreateAcountActivity.class));
                return true;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void switchToAccountsFragment(Category category) {
        KLog.d();
        Bundle bundle = new Bundle();
        bundle.putParcelable(CreateAcountActivity.EXTRA_CATEGORY, category);
        accountsFragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_content, accountsFragment);
        transaction.commit();
    }

}
