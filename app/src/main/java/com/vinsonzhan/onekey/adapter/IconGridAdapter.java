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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.beardedhen.androidbootstrap.BootstrapText;
import com.beardedhen.androidbootstrap.font.FontAwesome;
import com.socks.library.KLog;
import com.vinsonzhan.onekey.R;

/**
 * project:onekey
 * email：zhanwjwit@163.com
 * time：2/2/18 3:31 PM
 * author：Vinson.Zhan
 * comment：
 */
public class IconGridAdapter extends BaseAdapter {
    Context context;
    int count = 0;

    public IconGridAdapter(Context context) {
        this.context = context;
        this.count = 500;
    }

    Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void onItemClick(View view, String iconStr);
    }

    @Override public int getCount() {
        return count;
    }

    public void setCount(int count) {
        if (new FontAwesome().iconCodeForAttrIndex(count) == null)
            throw new IllegalArgumentException(count + " is too large, set small value");
        this.count = count;
    }

    @Override public String getItem(int position) {
        CharSequence icon = new FontAwesome().iconCodeForAttrIndex(position);
        if (icon == null)
            throw new IllegalArgumentException(count + " is not valid");
        return icon.toString();
    }

    @Override public long getItemId(int position) {
        return position;
    }

    @Override public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.item_category_icon,
                    null);

        AwesomeTextView awesomeTextView = convertView.findViewById(R.id.icon);
        awesomeTextView.setBootstrapText(new BootstrapText.Builder(context)
        .addFontAwesomeIcon(getItem(position)).build());

        awesomeTextView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                KLog.d(getItem(position));
                if (listener != null) listener.onItemClick(v, getItem(position));
            }
        });

        return convertView;
    }
}
