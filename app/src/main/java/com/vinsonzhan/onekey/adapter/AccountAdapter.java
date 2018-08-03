package com.vinsonzhan.onekey.adapter;

import android.support.annotation.Nullable;

import com.socks.library.KLog;
import com.vinsonzhan.onekey.model.Account;
import com.vinsonzhan.onekey.model.Category;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;

public class AccountAdapter extends FlexibleAdapter<Account> {
    public AccountAdapter(@Nullable List<Account> items) {
        super(items);
    }

    public AccountAdapter(@Nullable List<Account> items, @Nullable Object listeners) {
        super(items, listeners);
    }

    @Nullable
    @Override
    public Account getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


}
