package com.mall.android.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mall.android.R;


/**
 * Created by zhoubo on 2017/2/19.
 */

public class BottomNavigationItem extends LinearLayout {

    private static final String TAG = BottomNavigationItem.class.getSimpleName();

    private View root;
    private int mIcon;
    private int mColor;
    private int label;

    private LinearLayout itemLayout;
    private ImageView iconImageView;
    private TextView labelTextView;

    public BottomNavigationItem(Context context) {
        this(context, null);
    }

    public BottomNavigationItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomNavigationItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BottomNavigationItem);
        mIcon = a.getResourceId(R.styleable.BottomNavigationItem_icon, 0);
        label = a.getResourceId(R.styleable.BottomNavigationItem_label, 0);
        mColor = a.getResourceId(R.styleable.BottomNavigationItem_labelColor, 0);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        root = inflater.inflate(R.layout.bottom_navigation_bar_item_lay, this, true);
        iconImageView = (ImageView) root.findViewById(R.id.icon_imageView);
        labelTextView = (TextView) root.findViewById(R.id.label_textView);

        iconImageView.setImageResource(mIcon);
        labelTextView.setText(getResources().getString(label));
        labelTextView.setTextColor(getResources().getColorStateList(mColor));

        a.recycle();
    }

    public void select(boolean isSelected){
        iconImageView.setSelected(isSelected);
        labelTextView.setSelected(isSelected);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        itemLayout = (LinearLayout) root.findViewById(R.id.bottom_item_lay);
        itemLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onSelectedListener != null) onSelectedListener.onSelected(getId());
            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        onSelectedListener = null;
        Log.d(BottomNavigationBar.TAG, TAG + " onDetachedFromWindow()");
    }

    OnSelectedListener onSelectedListener;

    public void setOnSelectedListener(OnSelectedListener onSelectedListener) {
        this.onSelectedListener = onSelectedListener;
    }

    interface OnSelectedListener{
        void onSelected(int id);
    }
}
