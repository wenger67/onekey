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
import com.beardedhen.androidbootstrap.font.FontAwesome;
import com.vinsonzhan.onekey.App;
import com.vinsonzhan.onekey.R;
import com.vinsonzhan.onekey.adapter.ItemType;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.flexibleadapter.items.IFlexible;
import eu.davidea.viewholders.FlexibleViewHolder;

/**
 * project:onekey
 * email：zhanwjwit@163.com
 * time：1/22/18 5:47 PM
 * author：Vinson.Zhan
 * comment：
 */

@Entity
public class Account extends AbstractFlexibleItem<Account.ViewHolder> implements Parcelable {

    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel source) {
            return new Account(source);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };
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

    @Override
    public String toString() {
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

    @Override
    public int getItemViewType() {
        return ItemType.TYPE_ACCOUNT;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.category);
        dest.writeString(this.title);
        dest.writeString(this.userName);
        dest.writeString(this.password);
        dest.writeString(this.comment);
        dest.writeValue(this.createdTime);
        dest.writeValue(this.modifiedTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account that = (Account) o;
        return id.equals(that.id);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_account;
    }

    @Override
    public ViewHolder createViewHolder(View view, FlexibleAdapter adapter) {
        return new ViewHolder(view, adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter<IFlexible> adapter, ViewHolder holder, int
            position, List<Object> payloads) {
        Account account = (Account) adapter.getItem(position);
        if (account == null) return;
        holder.title.setText(account.getTitle());
        holder.username.setText(account.getUserName());
        holder.password.setText(account.getPassword());
        holder.usernameIcon.setBootstrapText(new BootstrapText.Builder(App.getInstance())
                .addFontAwesomeIcon(FontAwesome.FA_USER_CIRCLE).build());

//        final BootstrapButton del = helper.getView(R.id.account_del);
//        final BootstrapButton mod = helper.getView(R.id.account_mod);
//
//        final AnimatorSet set = getAnimatorSet(helper, account);
    }

    public static class ViewHolder extends FlexibleViewHolder {
        TextView title;
        TextView username;
        TextView password;
        AwesomeTextView usernameIcon;

        private ViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            this.title = view.findViewById(R.id.account_title_tv);
            this.username = view.findViewById(R.id.account_username_tv);
            this.password = view.findViewById(R.id.account_password_tv);
            this.usernameIcon = view.findViewById(R.id.account_username_icon);
        }
    }
}
