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
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.blankj.utilcode.util.SnackbarUtils;
import com.socks.library.KLog;
import com.vinsonzhan.onekey.R;
import com.vinsonzhan.onekey.adapter.CategoryAdapter;
import com.vinsonzhan.onekey.adapter.OpsType;
import com.vinsonzhan.onekey.common.OpsListener;
import com.vinsonzhan.onekey.model.Category;
import com.vinsonzhan.onekey.util.DataUtil;

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
public class CategoriesFragment extends Fragment implements OpsListener, FlexibleAdapter
        .OnItemClickListener, FlexibleAdapter.OnItemLongClickListener{

    private Unbinder unbinder;
    @BindView(R.id.rv_category)
    RecyclerView recyclerView;

    CategoryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        unbinder = ButterKnife.bind(this, view);
        List<Category> categories = DataUtil.getData();
        adapter = new CategoryAdapter(categories, this);
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
        Category category = adapter.getItem(position);
        KLog.d(category);
        if (category == null) return false;
        if (category.getCount() == 0)
            SnackbarUtils.with(recyclerView)
                    .setMessage(getString(R.string.category_click_tips))
                    .showWarning();
        else {
            MainActivity activity = (MainActivity) getActivity();
            if (activity != null)
                activity.switchToAccountsFragment(category);
            else KLog.d("activity is null");
        }
        return true;
    }

    @Override
    public void onItemLongClick(int position) {
        KLog.d();
//        Category category = adapter.getItem(position);
//        Intent intent = new Intent(this, CreateAcountActivity.class);
//        intent.putExtra(CreateAcountActivity.EXTRA_CATEGORY, category);
//        ActivityUtils.startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
