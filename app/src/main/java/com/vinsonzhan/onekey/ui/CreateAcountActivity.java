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
import android.database.sqlite.SQLiteCursor;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapText;
import com.beardedhen.androidbootstrap.font.FontAwesome;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SnackbarUtils;
import com.socks.library.KLog;
import com.vinsonzhan.onekey.App;
import com.vinsonzhan.onekey.R;
import com.vinsonzhan.onekey.adapter.CategorySpinnerAdapter;
import com.vinsonzhan.onekey.common.CreateAccountMode;
import com.vinsonzhan.onekey.greendao.AccountDao;
import com.vinsonzhan.onekey.greendao.CategoryDao;
import com.vinsonzhan.onekey.model.Account;
import com.vinsonzhan.onekey.model.Category;
import com.vinsonzhan.onekey.util.Constants;
import com.vinsonzhan.onekey.util.DataUtil;

import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * project:onekey
 * email：zhanwjwit@163.com
 * time：1/24/18 7:18 PM
 * author：Vinson.Zhan
 * comment：
 */
public class CreateAcountActivity extends BaseActivity {

    public static final String EXTRA_CATEGORY = "extra_category";
    public static final String EXTRA_ACCOUNT = "extra_account";
    Category mOldCategory;
    Category mCurCategory;
    Account mCurAccount;
    String title = "";
    String userName = "";
    String password = "";
    String comment = "";
    @CreateAccountMode int mode = CreateAccountMode.CREATE;
    @BindView(R.id.create_title) EditText titleEt;
    @BindView(R.id.create_account) EditText accountEt;
    @BindView(R.id.create_password) EditText passwordEt;
    @BindView(R.id.create_comment) EditText commentEt;

    @BindView(R.id.category_selector) LinearLayout categorySelector;
    @BindView(R.id.category_spinner) Spinner categorySpinner;
    @BindView(R.id.category_creator) BootstrapButton categoryCreator;

    @BindView(R.id.create_save) BootstrapButton saveBtn;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.account));
        setIcon(FontAwesome.FA_ARROW_LEFT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        setupActionBar();

        ButterKnife.bind(this);

        mCurCategory = getIntent().getParcelableExtra(EXTRA_CATEGORY);
        if (mCurCategory == null) {
            QueryBuilder<Category> qb = App.getDaoSession().getCategoryDao().queryBuilder();
            String defaultName = Constants.CATAGORY_MAP.get(Constants.EMAIL);
            WhereCondition condition = CategoryDao.Properties.Name.eq(defaultName);
            mCurCategory = qb.where(condition).unique();
        }

        initView();

        initData();
    }

    private void initData() {
        mCurAccount = getIntent().getParcelableExtra(EXTRA_ACCOUNT);
        if (mCurAccount != null) {
            mode = CreateAccountMode.MODIFY;
            mOldCategory = DataUtil.getCategoryByName(mCurAccount.getCategory());

            title = mCurAccount.getTitle();
            userName = mCurAccount.getUserName();
            password = mCurAccount.getPassword();
            comment = mCurAccount.getComment();

            titleEt.setText(title);
            accountEt.setText(userName);
            passwordEt.setText(password);
            commentEt.setText(comment);
        }
    }


    @OnTextChanged(
            value = {R.id.create_title, R.id.create_account, R.id.create_password, R.id
                    .create_comment},
            callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterTextChanged(Editable s) {
        KLog.d();
        if (getCurrentFocus() != null) {
            switch (getCurrentFocus().getId()) {
                case R.id.create_title:
                    title = s.length() == 0 ? "" : s.toString();
                    break;
                case R.id.create_account:
                    userName = s.length() == 0 ? "" : s.toString();
                    break;
                case R.id.create_password:
                    password = s.length() == 0 ? "" : s.toString();
                    break;
                case R.id.create_comment:
                    comment = s.length() == 0 ? "" : s.toString();
                    break;
            }
        }
    }

    @OnClick(R.id.create_save)
    void onClick(View view) {
        if (title.isEmpty() || userName.isEmpty() || password.isEmpty()) {
            SnackbarUtils.with(saveBtn).setMessage(getString(R.string.not_complete_tip))
                    .showWarning();
        } else {
            Long time = SystemClock.currentThreadTimeMillis();
            AccountDao accountDao = App.getDaoSession().getAccountDao();
            if (mode == CreateAccountMode.CREATE) {
                Account account = new Account(null, mCurCategory.getName(), title, userName, password,
                        comment, time, time);
                accountDao.insert(account);
            } else if (mode == CreateAccountMode.MODIFY) {
                mCurAccount.setTitle(title);
                mCurAccount.setCategory(mCurCategory.getName());
                mCurAccount.setUserName(userName);
                mCurAccount.setPassword(password);
                mCurAccount.setComment(comment);
                mCurAccount.setModifiedTime(time);
                accountDao.update(mCurAccount);
                // update old category info
                updateCategoryTable(mOldCategory);
            }
            // update new category info
            updateCategoryTable(mCurCategory);
            finish();
        }
    }

    private void updateCategoryTable(Category category) {
        QueryBuilder<Account> qb = App.getDaoSession().getAccountDao().queryBuilder();
        long count = qb.where(AccountDao.Properties.Category.eq(category.getName())).count();

        Category newCategory = new Category(category.getId(), category.getName(), category
                .getIcon(), category.getIsDefault(), (int) count);

        App.getDaoSession().getCategoryDao().update(newCategory);
    }

    @OnTextChanged(
            value = {R.id.create_title, R.id.create_account, R.id.create_password},
            callback = OnTextChanged.Callback.TEXT_CHANGED)
    void onTextChanged(CharSequence s, int start, int before, int count) {
        KLog.d();
        if (s.length() > 32) {
            SnackbarUtils.with(saveBtn).setMessage(getString(R.string.exceed_length_tip))
                    .showWarning();
        }
    }

    @OnTextChanged(
            value = {R.id.create_title, R.id.create_account, R.id.create_password, R.id
                    .create_comment},
            callback = OnTextChanged.Callback.BEFORE_TEXT_CHANGED)
    void beforeTextChanged(CharSequence s, int start, int count, int after) {
        KLog.d();
    }

    @Override protected void onResume() {
        super.onResume();
    }

    private void initView() {
        QueryBuilder<Category> qb = App.getDaoSession().getCategoryDao().queryBuilder();
        final CategorySpinnerAdapter adapter = new CategorySpinnerAdapter(this, qb.buildCursor().query(), true);
        categorySpinner.setAdapter(adapter);

        // init spinner default selection
        String name = mCurCategory.getName();
        for (int i = 0; i < adapter.getCount(); i++) {
            SQLiteCursor sc = (SQLiteCursor) adapter.getItem(i);
            if (name.equals(sc.getString(sc.getColumnIndex(CategoryDao.Properties.Name
                    .columnName)))) {
                categorySpinner.setSelection(i);
                break;
            }
        }

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SQLiteCursor sc = (SQLiteCursor) adapter.getItem(position);
                String name = sc.getString(sc.getColumnIndex(CategoryDao.Properties.Name.columnName));
                KLog.d(name + ", " + position);
                QueryBuilder<Category> qb = App.getDaoSession().getCategoryDao().queryBuilder();
                Category c = qb.where(CategoryDao.Properties.Name.eq(name)).unique();
                KLog.d(c);
                mCurCategory = c;
            }

            @Override public void onNothingSelected(AdapterView<?> parent) {
                KLog.d();
            }
        });

        categoryCreator.setBootstrapText(new BootstrapText.Builder(this)
        .addFontAwesomeIcon(FontAwesome.FA_PLUS).build());

        categoryCreator.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                ActivityUtils.startActivity(new Intent(CreateAcountActivity.this,
                        CreateCategoryActivity.class));
                // todo
            }
        });
    }

    @Override protected void onDestroy() {
        super.onDestroy();
    }
}
