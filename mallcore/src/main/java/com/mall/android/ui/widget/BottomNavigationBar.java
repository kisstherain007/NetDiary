package com.mall.android.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mall.android.R;


/**
 * Created by zhoubo on 2017/2/19.
 */

public class BottomNavigationBar extends LinearLayout {

    public static final String TAG = BottomNavigationBar.class.getSimpleName();

    private SparseArray<BottomNavigationItem> bottomNavigationItemList;

    private int layoutResId;

    ViewGroup root;

    private int mSelectedId = -1;

    public BottomNavigationBar(Context context) {
        this(context, null);
    }

    public BottomNavigationBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomNavigationBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BottomNavigationBar);
        layoutResId = a.getResourceId(R.styleable.BottomNavigationBar_menu, 0);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        root = (ViewGroup) inflater.inflate(layoutResId, this, true);

        a.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (bottomNavigationItemList == null) bottomNavigationItemList = new SparseArray<>();

        if (getChildCount() == 1) {
            ViewGroup bottomNavigationItems = (ViewGroup) getChildAt(0);
            int childCount = bottomNavigationItems.getChildCount();
            for (int index = 0; index < childCount; index++){
                View view = bottomNavigationItems.getChildAt(index);
                if (view instanceof BottomNavigationItem){
                    BottomNavigationItem bottomNavigationItem = (BottomNavigationItem) view;
                    bottomNavigationItemList.append(bottomNavigationItem.getId(), bottomNavigationItem);
                    bottomNavigationItem.setOnSelectedListener(new BottomNavigationItem.OnSelectedListener() {
                        @Override
                        public void onSelected(int id) {
                            if (mSelectedId == id) return;
                            if (onTabSelectedListener != null) onTabSelectedListener.onSelected(id);
                            setCheckedTab(id, true);
                        }
                    });
                }
            }
        }
    }

    public void setCheckedTab(int newSelectedId, boolean isChecked){
        int oldSelected = mSelectedId;
        if (mSelectedId != newSelectedId){
            bottomNavigationItemList.get(newSelectedId).select(true);
            if (oldSelected != -1) bottomNavigationItemList.get(oldSelected).select(false);
        }

        mSelectedId = newSelectedId;

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (bottomNavigationItemList != null){
            bottomNavigationItemList.clear();
        }
        Log.d(TAG, TAG + " onDetachedFromWindow");
    }

    OnTabSelectedListener onTabSelectedListener;

    public void setOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener) {
        this.onTabSelectedListener = onTabSelectedListener;
    }

    public interface OnTabSelectedListener{
        void onSelected(int id);
    }
}
