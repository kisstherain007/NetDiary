package com.bz.netdiary.ui.fragment.profile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.bz.netdiary.R;
import com.mall.android.ui.fragment.BaseFragment;

import java.util.List;

/**
 * Created by zhoubo on 2017/5/15.
 */

public class ProfileFragment extends BaseFragment {

    TextView titleView;
    ViewPager viewPager;
    List<Fragment> fragmentList;

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    protected int inflateContentView() {
        return R.layout.profile_lay;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
