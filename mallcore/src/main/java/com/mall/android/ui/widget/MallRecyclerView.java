package com.mall.android.ui.widget;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by zhoubo on 2017/2/24.
 */

public class MallRecyclerView extends RecyclerView {

    private static final String TAG = MallRecyclerView.class.getSimpleName();

    public MallRecyclerView(Context context) {
        super(context);
    }

    public MallRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MallRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
