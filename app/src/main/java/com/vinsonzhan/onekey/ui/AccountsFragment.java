package com.vinsonzhan.onekey.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.socks.library.KLog;
import com.vinsonzhan.onekey.R;
import com.vinsonzhan.onekey.adapter.AccountAdapter;
import com.vinsonzhan.onekey.adapter.OpsType;
import com.vinsonzhan.onekey.common.OpsListener;
import com.vinsonzhan.onekey.model.Account;
import com.vinsonzhan.onekey.model.Category;
import com.vinsonzhan.onekey.util.DataUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.IFlexible;

/**
 * project:onekey
 * email: zhanwit@163.com
 * time: 2018/12/29 10:49
 * author: Vinson. Zhan
 * comment: ${DESCRIPTION}
 */
public class AccountsFragment extends Fragment implements OpsListener, FlexibleAdapter
        .OnItemClickListener, FlexibleAdapter.OnItemLongClickListener {

    @BindView(R.id.rv_accout) RecyclerView recyclerView;
    RelativeLayout item_category;
    AwesomeTextView category_icon;
    TextView category_count;
    TextView category_name;
    Category mCurCategory;
    AccountAdapter adapter;
    List<Account> accounts = new ArrayList<>();
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        KLog.d();
        View view = inflater.inflate(R.layout.fragment_accounts, container, false);
        unbinder = ButterKnife.bind(this, view);
        item_category = view.findViewById(R.id.item_category);
        category_icon = item_category.findViewById(R.id.category_icon);
        category_name = item_category.findViewById(R.id.category_name);
        category_count = item_category.findViewById(R.id.category_count);

        if (getArguments() != null) {
            mCurCategory = getArguments().getParcelable(CreateAcountActivity.EXTRA_CATEGORY);
            if (mCurCategory != null) accounts = DataUtil.getAccountsByCategory(mCurCategory);
            else KLog.d("category object is null!");
        }

        if (mCurCategory != null) {
            category_icon.setFontAwesomeIcon(mCurCategory.getIcon());
            category_name.setText(mCurCategory.getName());
            category_count.setText(String.valueOf(mCurCategory.getCount()));
        }

        adapter = new AccountAdapter(accounts, this);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onOpsEvent(IFlexible data, @OpsType int opsType) {

    }

    private void snackSuccess(String msg) {
        TSnackbar snackbar = TSnackbar.make(recyclerView, msg, TSnackbar.LENGTH_LONG);
        View view = snackbar.getView();
        view.setBackgroundColor(getResources().getColor(R.color.message));
        TextView textView = (TextView) view.findViewById(com.androidadvance.topsnackbar.R.id
                .snackbar_text);
        textView.setTextColor(getResources().getColor(R.color.success));
        snackbar.show();
    }

    @Override
    public boolean onItemClick(View view, int position) {
        KLog.d();
        return true;
    }

    @Override
    public void onItemLongClick(int position) {
        KLog.d();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
