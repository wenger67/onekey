package com.vinsonzhan.onekey.adapter;


import android.support.annotation.IntDef;

import static com.vinsonzhan.onekey.adapter.ItemType.TYPE_ACCOUNT;
import static com.vinsonzhan.onekey.adapter.ItemType.TYPE_ACCOUNT_FOOT;
import static com.vinsonzhan.onekey.adapter.ItemType.TYPE_CATEGORY;

@IntDef({TYPE_CATEGORY, TYPE_ACCOUNT, TYPE_ACCOUNT_FOOT})
public @interface ItemType {
    int TYPE_CATEGORY = 0;
    int TYPE_ACCOUNT = 1;
    int TYPE_ACCOUNT_FOOT = 2;
}
