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

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.vinsonzhan.onekey.adapter.ExpandableItemAdapter;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * project:onekey
 * email：zhanwjwit@163.com
 * time：1/22/18 5:47 PM
 * author：Vinson.Zhan
 * comment：
 */

@Entity
public class Account implements MultiItemEntity, Parcelable {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "CATEGORY_NAME")
    private String category;

    @Property(nameInDb = "ACCOUNT_TITLE")
    private String title;

    @Property(nameInDb = "ACCOUNT_USERNAME")
    private String userName;

    @Property(nameInDb = "ACCOUNT_PASSWORD")
    private String password;

    @Property(nameInDb = "ACCOUNT_COMMENT")
    private String comment;

    @Property(nameInDb = "CREATE_TIME")
    private Long createdTime;

    @Property(nameInDb = "MODIFIED_TIME")
    private Long modifiedTime;

    @Generated(hash = 1571056890)
    public Account(Long id, String category, String title, String userName,
            String password, String comment, Long createdTime, Long modifiedTime) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.userName = userName;
        this.password = password;
        this.comment = comment;
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
    }

    @Generated(hash = 882125521)
    public Account() {
    }

    @Override public String toString() {
        return "Account{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", title='" + title + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", comment='" + comment + '\'' +
                ", createdTime=" + createdTime +
                ", modifiedTime=" + modifiedTime +
                '}';
    }

    @Override public int getItemType() {
        return ExpandableItemAdapter.TYPE_ACCOUNT;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getCreatedTime() {
        return this.createdTime;
    }

    public void setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
    }

    public Long getModifiedTime() {
        return this.modifiedTime;
    }

    public void setModifiedTime(Long modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override public int describeContents() { return 0; }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.category);
        dest.writeString(this.title);
        dest.writeString(this.userName);
        dest.writeString(this.password);
        dest.writeString(this.comment);
        dest.writeValue(this.createdTime);
        dest.writeValue(this.modifiedTime);
    }

    protected Account(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.category = in.readString();
        this.title = in.readString();
        this.userName = in.readString();
        this.password = in.readString();
        this.comment = in.readString();
        this.createdTime = (Long) in.readValue(Long.class.getClassLoader());
        this.modifiedTime = (Long) in.readValue(Long.class.getClassLoader());
    }

    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override public Account createFromParcel(Parcel source) {return new Account(source);}

        @Override public Account[] newArray(int size) {return new Account[size];}
    };
}
