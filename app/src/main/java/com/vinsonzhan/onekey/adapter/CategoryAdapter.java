package com.vinsonzhan.onekey.adapter;

import android.support.annotation.Nullable;

import com.vinsonzhan.onekey.model.Category;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;

public class CategoryAdapter extends FlexibleAdapter<Category> {
    public CategoryAdapter(@Nullable List<Category> items) {
        super(items);
    }

    public CategoryAdapter(@Nullable List<Category> items, @Nullable Object listeners) {
        super(items, listeners);
    }

    @Nullable
    @Override
    public Category getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


}
