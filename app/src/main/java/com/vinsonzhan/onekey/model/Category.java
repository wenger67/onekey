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

package com.vinsonzhan.onekey.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.beardedhen.androidbootstrap.BootstrapText;
import com.vinsonzhan.onekey.App;
import com.vinsonzhan.onekey.R;
import com.vinsonzhan.onekey.adapter.ItemType;
import com.vinsonzhan.onekey.common.DataChangeListener;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.flexibleadapter.items.IFlexible;
import eu.davidea.viewholders.FlexibleViewHolder;

/**
 * project:onekey
 * email：zhanwjwit@163.com
 * time：1/19/18 5:43 PM
 * author：Vinson.Zhan
 * comment：
 */
@Entity
public class Category extends AbstractFlexibleItem<Category.ViewHolder> implements Parcelable {
    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel source) {
            return new Category(source);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
    @Id(autoincrement = true)
    private Long id;
    @Property(nameInDb = "CATEGORY_NAME")
    private String name;
    @Property(nameInDb = "CATEGORY_ICON")
    private String icon;
    @Property(nameInDb = "CATEGORY_DEFAULT")
    private boolean isDefault;
    @Property(nameInDb = "CATEGORY_COUNT")
    private int count;
    // not store into db
    @Transient
    private DataChangeListener dataChangeListener;

    @Generated(hash = 381780247)
    public Category(Long id, String name, String icon, boolean isDefault, int count) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.isDefault = isDefault;
        this.count = count;
    }


    @Generated(hash = 1150634039)
    public Category() {
    }


    protected Category(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.name = in.readString();
        this.icon = in.readString();
        this.isDefault = in.readByte() != 0;
        this.count = in.readInt();
    }

    public void setDataChangeListener(DataChangeListener dataChangeListener) {
        this.dataChangeListener = dataChangeListener;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getCount() {
        return this.count;
    }

    // TODO: 2/6/18 long
    public void setCount(int count) {
        if (this.count != count) {
            this.count = count;
        }

        if (dataChangeListener != null) {
            dataChangeListener.onDataChange(count);
        }
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", count=" + count +
                '}';
    }

    @Override
    public int getItemViewType() {
        return ItemType.TYPE_CATEGORY;
    }

    public boolean getIsDefault() {
        return this.isDefault;
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeString(this.icon);
        dest.writeByte(this.isDefault ? (byte) 1 : (byte) 0);
        dest.writeInt(this.count);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category that = (Category) o;
        return id.equals(that.id);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_category;
    }

    @Override
    public ViewHolder createViewHolder(View view, FlexibleAdapter<IFlexible> adapter) {
        return new ViewHolder(view, adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter<IFlexible> adapter, ViewHolder holder, int
            position, List<Object> payloads) {
        Category category = (Category) adapter.getItem(position);
        holder.icon.setBootstrapText(new BootstrapText.Builder(App.getInstance())
                .addFontAwesomeIcon(category.getIcon()).build());

        holder.name.setText(category.getName());

        holder.count.setTextColor(category.getCount() > 0 ? App.getRes().getColor(R.color
                .colorPrimaryDark) : App.getRes().getColor(R.color.bg_gray));
        holder.count.setText(String.valueOf(category.getCount()));
    }

    public static class ViewHolder extends FlexibleViewHolder {
        AwesomeTextView icon;
        TextView name;
        TextView count;

        private ViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            this.icon = view.findViewById(R.id.category_icon);
            this.name = view.findViewById(R.id.category_name);
            this.count = view.findViewById(R.id.category_count);
        }
    }
}
