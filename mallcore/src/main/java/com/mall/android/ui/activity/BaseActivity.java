package com.mall.android.ui.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;

import com.mall.android.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoubo on 2017/2/17.
 */

public class BaseActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        this.mToolbar = (Toolbar)this.findViewById(R.id.toolbar);
        if(this.mToolbar != null) {
            this.setSupportActionBar(this.mToolbar);
        }
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }
}
