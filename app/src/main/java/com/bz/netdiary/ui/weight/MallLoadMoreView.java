package com.bz.netdiary.ui.weight;

import com.bz.netdiary.R;
import com.chad.library.adapter.base.loadmore.LoadMoreView;

/**
 * Created by zhoubo on 2017/3/2.
 */

public class MallLoadMoreView extends LoadMoreView {

    @Override public int getLayoutId() {
        return R.layout.view_load_more;
    }

    @Override protected int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }

    @Override protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }

    @Override protected int getLoadEndViewId() {
        return R.id.load_more_load_end_view;
    }
}
