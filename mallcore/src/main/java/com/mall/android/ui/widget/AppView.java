package com.mall.android.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by zhoubo on 2017/2/25.
 */

public abstract class AppView extends LinearLayout {

    public Context mContext;

    public AppView(Context context) {
        super(context);
        this.mContext = context;
    }

    public AppView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public AppView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    public abstract void onViewCreated();

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (isInEditMode()) return;
        onViewCreated();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
