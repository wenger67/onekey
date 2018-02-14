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

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.vinsonzhan.onekey.adapter.ExpandableItemAdapter;
import com.vinsonzhan.onekey.common.DataChangeListener;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;

/**
 * project:onekey
 * email：zhanwjwit@163.com
 * time：1/19/18 5:43 PM
 * author：Vinson.Zhan
 * comment：
 */
@Entity
public class Category extends AbstractExpandableItem<Account> implements MultiItemEntity,
        Parcelable {
    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override public Category createFromParcel(Parcel source) {return new Category(source);}

        @Override public Category[] newArray(int size) {return new Category[size];}
    };
    @Id(autoincrement = true)
    private Long id;
    @Property(nameInDb = "CATEGORY_NAME")
    private String name;
    @Property(nameInDb = "CATEGORY_ICON")
    private String icon;
    @Property(nameInDb = "CATEGORY_DEFAULT")
    private boolean isDefault;
    private int count;

    public void setDataChangeListener(DataChangeListener dataChangeListener) {
        this.dataChangeListener = dataChangeListener;
    }

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
        if (this.count != count && dataChangeListener != null) {
            this.count = count;
            dataChangeListener.onDataChange(count);
        }
    }

    @Override public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", count=" + count +
                '}';
    }

    @Override public int getItemType() {
        return ExpandableItemAdapter.TYPE_CATEGORY;
    }

    @Override public int getLevel() {
        return 0;
    }

    public boolean getIsDefault() {
        return this.isDefault;
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    @Override public int describeContents() { return 0; }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeString(this.icon);
        dest.writeByte(this.isDefault ? (byte) 1 : (byte) 0);
        dest.writeInt(this.count);
    }
}
