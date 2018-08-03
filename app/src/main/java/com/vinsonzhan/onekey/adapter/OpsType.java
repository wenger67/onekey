package com.vinsonzhan.onekey.adapter;


import android.support.annotation.IntDef;

import static com.vinsonzhan.onekey.adapter.ItemType.TYPE_ACCOUNT;
import static com.vinsonzhan.onekey.adapter.ItemType.TYPE_ACCOUNT_FOOT;
import static com.vinsonzhan.onekey.adapter.ItemType.TYPE_CATEGORY;
import static com.vinsonzhan.onekey.adapter.OpsType.OPS_COPY_NAME;
import static com.vinsonzhan.onekey.adapter.OpsType.OPS_COPY_PWD;
import static com.vinsonzhan.onekey.adapter.OpsType.OPS_COPY_TITLE;
import static com.vinsonzhan.onekey.adapter.OpsType.OPS_DELETE;
import static com.vinsonzhan.onekey.adapter.OpsType.OPS_MODIFY;

@IntDef({OPS_DELETE, OPS_MODIFY, OPS_COPY_TITLE, OPS_COPY_NAME, OPS_COPY_PWD})
public @interface OpsType {
    int OPS_DELETE = 0;
    int OPS_MODIFY = 1;
    int OPS_COPY_TITLE = 2;
    int OPS_COPY_NAME = 3;
    int OPS_COPY_PWD = 4;
}
