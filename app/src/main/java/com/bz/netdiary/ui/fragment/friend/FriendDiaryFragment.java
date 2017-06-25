package com.bz.netdiary.ui.fragment.friend;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import com.bz.netdiary.R;
import com.bz.netdiary.ui.fragment.adapter.AppFragmentPagerAdapter;
import com.bz.netdiary.ui.fragment.adapter.AppTitleFragmentPagerAdapter;
import com.mall.android.ui.fragment.BaseFragment;
import com.mall.android.ui.widget.slidingTab.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zhoubo on 2017/5/17.
 */

public class FriendDiaryFragment extends BaseFragment implements ViewPager.OnPageChangeListener{

    private TabLayout tab_title;
    ViewPager viewPager;
    List<Fragment> fragmentList;
    int mCurrentPosition = 0;


    public static FriendDiaryFragment newInstance() {
        FriendDiaryFragment fragment = new FriendDiaryFragment();
        return fragment;
    }

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_friend_diary_lay;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

//        slidingTabs = (SlidingTabLayout) view.findViewById(R.id.tablayout);
        tab_title = (TabLayout) view.findViewById(R.id.tablayout);

        fragmentList = new ArrayList<>();
        fragmentList.add(WebDiaryFragment.newInstance());
        fragmentList.add(PhotoDiaryFragment.newInstance());

        String[] titleArr = {"网记", "相册"};

        tab_title.addTab(tab_title.newTab().setText(titleArr[0]));
        tab_title.addTab(tab_title.newTab().setText(titleArr[1]));

        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        viewPager.setAdapter(new AppTitleFragmentPagerAdapter(getChildFragmentManager(), fragmentList, titleArr));
        viewPager.setCurrentItem(mCurrentPosition);
        /**************************************************************/

        tab_title.setupWithViewPager(viewPager);

//        slidingTabs.setCustomTabView(R.layout.comm_lay_tab_indicator, android.R.id.text1);
//        slidingTabs.setSelectedIndicatorColors(getResources().getColor(R.color.blue_sky));
//        slidingTabs.setDistributeEvenly(true); //是否填充满屏幕的宽度
//        slidingTabs.setViewPager(viewPager);
//        slidingTabs.setOnPageChangeListener(this);
//        slidingTabs.setCurrent(mCurrentPosition);

//        mAppBarLayout= (AppBarLayout) view.findViewById(R.id.AppFragment_AppBarLayout);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /** 根据百分比改变颜色透明度 */
    public int changeAlpha(int color, float fraction) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        int alpha = (int) (Color.alpha(color) * fraction);
        return Color.argb(alpha, red, green, blue);
    }

    /**
     * 使用CollapsingToolbarLayout必须把title设置到CollapsingToolbarLayout上，
     * 设置到Toolbar上则不会显示
     */
    AppBarLayout appBarLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    private void setTitleToCollapsingToolbarLayout() {
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset <= -200 / 2) {
                    collapsingToolbarLayout.setTitle("黄晓果");
                    //使用下面两个CollapsingToolbarLayout的方法设置展开透明->折叠时你想要的颜色
                    collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
                    collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorAccent));
                } else {
                    collapsingToolbarLayout.setTitle("");
                }
            }
        });
    }
}
