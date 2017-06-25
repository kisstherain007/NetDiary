package com.mall.android.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ViewFlipper;

import com.mall.android.R;

/**
 * Created by zhoubo on 2017/3/21.
 */

public class MarqueeView extends AppView {

    private ViewFlipper viewFlipper;

    public MarqueeView(Context context) {
        super(context);
    }

    public MarqueeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MarqueeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onViewCreated() {
        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        viewFlipper.addView(View.inflate(getContext(), R.layout.marquee_item_view, null));
        viewFlipper.addView(View.inflate(getContext(), R.layout.marquee_item_view, null));
    }
}
