package com.bz.netdiary.ui.fragment.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mall.android.common.utils.Logger;

import java.io.Serializable;

/**
 * Created by zhoubo on 2017/3/2.
 */

public abstract class AbstractRefreshFragment extends AbstractRecyclerFragment implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener{

    public static final String TAG = AbstractRefreshFragment.class.getSimpleName();

    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public int inflateContentView() {
        return com.mall.android.R.layout.comm_ui_recyclerview_swiperefresh;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(com.mall.android.R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        getRecyclerAdapter().setOnLoadMoreListener(this);
        getRecyclerAdapter().setEnableLoadMore(true);
    }

    @Override
    public void onRefresh() {
        Logger.d(TAG, "onRefresh()");
        mRefreshConfig.mCurrentCounter = mRefreshConfig.mPageSize;
        mRefreshConfig.refreshMode = RefreshMode.refresh;
        requestData(mRefreshConfig);
    }

    @Override
    public void onLoadMoreRequested() {
        Logger.d(TAG, "onLoadMoreRequested()");
        mSwipeRefreshLayout.setEnabled(false);
        if (getRecyclerAdapter().getData().size() < mRefreshConfig.mPageSize){
            getRecyclerAdapter().loadMoreEnd(true);
        }else {
            if (mRefreshConfig.mCurrentCounter >= mRefreshConfig.mTotlaCount){
                getRecyclerAdapter().loadMoreEnd(true);
            }else {
                // 开启网络请求
                mRefreshConfig.refreshMode = RefreshMode.loadMore;
                requestData(mRefreshConfig);
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mSwipeRefreshLayout.setOnRefreshListener(null);
        getRecyclerAdapter().setOnLoadMoreListener(null);
    }

    public void requestData(RefreshConfig refreshConfig){}

    public void onRequestComplete(RefreshConfig refreshConfig){
        if (refreshConfig.isSuccess){
            refreshConfig.mCurrentCounter = getRecyclerAdapter().getData().size();
            getRecyclerAdapter().loadMoreComplete();
        }else{
            getRecyclerAdapter().loadMoreFail();
        }

        refreshConfig.isSuccess = true; // 还原标识
        mSwipeRefreshLayout.setEnabled(true);
    }

    RefreshConfig mRefreshConfig = new RefreshConfig();

    public static class RefreshConfig implements Serializable {

        private static final long serialVersionUID = 6244426943442129360L;
        public int mPageSize = 6;
        public int mTotlaCount = 20;
        public int mCurrentCounter = 0;
        public RefreshMode refreshMode = RefreshMode.refresh;
        public boolean isSuccess = false;

        public RefreshConfig() {
        }
    }

    public static enum RefreshMode {
        refresh,
        loadMore;
        private RefreshMode() {
        }
    }
}
