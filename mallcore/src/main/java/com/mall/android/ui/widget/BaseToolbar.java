package com.mall.android.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;

/**
 * Created by zhoubo on 2017/2/17.
 */

public class BaseToolbar extends Toolbar {

    public BaseToolbar(Context context) {
        super(context);
    }

    public BaseToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
