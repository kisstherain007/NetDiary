package com.bz.netdiary.ui.fragment.base;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bz.netdiary.R;
import com.chad.library.adapter.base.BaseQuickAdapter;

/**
 * Created by zhoubo on 2017/3/7.
 */

public abstract class AbstractTableViewFragment<T> extends AbstractRefreshFragment {

    public static final int TABLEVIEW_LIST_TYPE = 0; // 列表类型
    public static final int TABLEVIEW_GRID_TYPE = 1; // 宫格类型
    private int tableview_default_type = TABLEVIEW_LIST_TYPE; // 默认列表类型
    private int tableView_grid_default_spanCount = 2; // 宫格默认列数
    private LinearLayoutManager linearLayoutManager;
    private LinearLayout tableView_top_container;
    private boolean mIsCanRefresh = false; // 是否支持刷新
    private boolean mIsCanLoadMore = false; // 是否可以加载更多
    public View recyclerHeaderView;

    @Override
    public int inflateContentView() {
        return R.layout.fragment_tableview_lay;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tableView_top_container = (LinearLayout) view.findViewById(R.id.tableView_top_container);
        addFragmentHeaderView(inflateFragmentHeaderView());
        addRecyclerViewHeaderView(inflateRecyclerViewHeaderView());
        mSwipeRefreshLayout.setEnabled(canRefresh());
        getRecyclerAdapter().setEnableLoadMore(canLoadMore());
    }

    public int inflateFragmentHeaderView(){
        return -1;
    }

    public int inflateRecyclerViewHeaderView(){
        return -1;
    }

    private View addFragmentHeaderView(int resLayoutId){
        if (resLayoutId == -1 || tableView_top_container == null) return null;
        View view = getLayoutInflater().inflate(resLayoutId, tableView_top_container, true);
        return view;
    }

    private View addRecyclerViewHeaderView(int resLayoutId){
        if (resLayoutId == -1) return null;
        recyclerHeaderView = getLayoutInflater().inflate(resLayoutId, (ViewGroup) getRecyclerView().getParent(), false);
        getRecyclerAdapter().addHeaderView(recyclerHeaderView);
        return recyclerHeaderView;
    }

    @Override
    public BaseQuickAdapter configRecyclerViewAdapter() {
        return null;
    }

    @Override
    public LinearLayoutManager configRecyclerViewLayoutManager() {

        tableview_default_type = tableViewDisplayType();

        switch (tableview_default_type){
            case TABLEVIEW_LIST_TYPE:
                linearLayoutManager = new LinearLayoutManager(getContext());
                break;
            case TABLEVIEW_GRID_TYPE:
                linearLayoutManager = new GridLayoutManager(getContext(), tableViewGridSpanCount());
                break;
            default:
                linearLayoutManager = new LinearLayoutManager(getContext());
                break;
        }
        return linearLayoutManager;
    }

    public int tableViewGridSpanCount(){
        return tableView_grid_default_spanCount;
    }

    public boolean canRefresh(){
        return mIsCanRefresh;
    }

    public boolean canLoadMore(){
        return mIsCanLoadMore;
    }

    protected abstract int tableViewDisplayType();

    public LinearLayoutManager getLinearLayoutManager() {
        return linearLayoutManager;
    }
}
