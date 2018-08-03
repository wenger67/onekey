package com.vinsonzhan.onekey.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
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
import com.socks.library.KLog;
import com.vinsonzhan.onekey.R;
import com.vinsonzhan.onekey.adapter.AccountAdapter;
import com.vinsonzhan.onekey.common.OpsListener;
import com.vinsonzhan.onekey.model.Account;
import com.vinsonzhan.onekey.model.Category;
import com.vinsonzhan.onekey.util.DataUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.IFlexible;

public class AccountListActivity extends BaseActivity implements OpsListener, FlexibleAdapter
        .OnItemClickListener, FlexibleAdapter.OnItemLongClickListener, FlexibleAdapter.OnItemSwipeListener {

    @BindView(R.id.rv_category)
    RecyclerView recyclerView;
    @BindView(R.id.navigation)
    NavigationView navigationView;
    @BindView(R.id.toolBar)
    android.support.v7.widget.Toolbar toolbar;
    @BindView(R.id.drawer)
    DrawerLayout drawerLayout;
    AccountAdapter adapter;
    Category mCurCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mCurCategory = getIntent().getParcelableExtra(CreateAcountActivity.EXTRA_CATEGORY);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayShowTitleEnabled(true);
        if (getSupportActionBar() != null) getSupportActionBar().setHomeButtonEnabled(true);
        toggle.syncState();

        List<Account> accounts = DataUtil.getAccountsByCategory(mCurCategory);

        adapter = new AccountAdapter(accounts, this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
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

    private void snackSuccess(String msg) {
        TSnackbar snackbar = TSnackbar.make(recyclerView, msg, TSnackbar.LENGTH_LONG);
        View view = snackbar.getView();
        view.setBackgroundColor(getResources().getColor(R.color.message));
        TextView textView = (TextView) view.findViewById(com.androidadvance.topsnackbar.R.id
                .snackbar_text);
        textView.setTextColor(getResources().getColor(R.color.success));
        snackbar.show();
    }

    @Override
    public boolean onItemClick(View view, int position) {
        KLog.d();
        return false;
    }

    @Override
    public void onItemLongClick(int position) {
        KLog.d();
    }

    @Override
    public void onOpsEvent(IFlexible data, int opsType) {

    }

    @Override
    public void onItemSwipe(int position, int direction) {
        KLog.d(position + ", " + direction);
    }

    @Override
    public void onActionStateChanged(RecyclerView.ViewHolder viewHolder, int actionState) {

    }
}
