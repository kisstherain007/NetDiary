package com.bz.netdiary.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.bz.netdiary.R;
import com.bz.netdiary.ui.fragment.adapter.AppFragmentPagerAdapter;
import com.bz.netdiary.ui.fragment.find.FindFragment;
import com.bz.netdiary.ui.fragment.profile.ProfileFragment;
import com.mall.android.ui.activity.BaseActivity;
import com.mall.android.ui.widget.tabar.BadgeDismissListener;
import com.mall.android.ui.widget.tabar.JPTabBar;
import com.mall.android.ui.widget.tabar.OnTabSelectListener;
import com.mall.android.ui.widget.tabar.anno.NorIcons;
import com.mall.android.ui.widget.tabar.anno.SeleIcons;
import com.mall.android.ui.widget.tabar.anno.Titles;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener, BadgeDismissListener, OnTabSelectListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Titles
    private static final String[] mTitles = {"消息","发现","我的"};

    @SeleIcons
    private static final int[] mSeleIcons = {R.mipmap.weixin_normal,R.mipmap.find_normal,R.mipmap.profile_normal};

    @NorIcons
    private static final int[] mNormalIcons = {R.mipmap.weixin_normal, R.mipmap.find_normal, R.mipmap.profile_normal};

    private static final String token = "XIfcO9rgaj9gnW/WQXOz78yGC5s1+h+bZ0kNyE2BV/PhCfSl/ZazkX0lDtqnCHG4+GSxTlo++SSL0PoGpCdq8w==";
    ViewPager viewPager;
    List<Fragment> fragmentList;
    private JPTabBar mTabbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentList = new ArrayList<>();
//        fragmentList.add(new ConversationListFragment());
        fragmentList.add(FindFragment.newInstance());
        fragmentList.add(ProfileFragment.newInstance());

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new AppFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
        viewPager.addOnPageChangeListener(this);

        initBottom();

//        connect(token);
    }

    private void initBottom() {

        mTabbar = (JPTabBar) findViewById(R.id.bottom_navigation_bar_container);
        mTabbar.setTitles("qwe","asd","qwe","asdsa")
                .setNormalIcons(R.mipmap.weixin_normal,R.mipmap.find_normal,R.mipmap.profile_normal)
                .setSelectedIcons(R.mipmap.weixin_normal,R.mipmap.find_normal,R.mipmap.profile_normal)
                .generate();

        mTabbar.setContainer(viewPager);
        mTabbar.setDismissListener(this);
        mTabbar.setUseScrollAnimate(true);
        mTabbar.setUseFilter(true);
        //显示圆点模式的徽章
        //设置容器
        mTabbar.showBadge(0, 1);
        //设置Badge消失的代理
        mTabbar.setTabListener(this);
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

    @Override
    public void onDismiss(int position) {

    }

    @Override
    public void onTabSelect(int index) {

    }

//    /**
//     * <p>连接服务器，在整个应用程序全局，只需要调用一次，需在 {@link #init(Context)} 之后调用。</p>
//     * <p>如果调用此接口遇到连接失败，SDK 会自动启动重连机制进行最多10次重连，分别是1, 2, 4, 8, 16, 32, 64, 128, 256, 512秒后。
//     * 在这之后如果仍没有连接成功，还会在当检测到设备网络状态变化时再次进行重连。</p>
//     *
//     * @param token    从服务端获取的用户身份令牌（Token）。
//     * @param callback 连接回调。
//     * @return RongIM  客户端核心类的实例。
//     */
//    private void connect(String token) {
//
////        if (getApplicationInfo().packageName.equals(App.getCurProcessName(getApplicationContext()))) {
//
//            RongIM.connect(token, new RongIMClient.ConnectCallback() {
//
//                /**
//                 * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
//                 *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
//                 */
//                @Override
//                public void onTokenIncorrect() {
//                    Log.d(TAG, "--onTokenIncorrect");
//                }
//
//                /**
//                 * 连接融云成功
//                 * @param userid 当前 token 对应的用户 id
//                 */
//                @Override
//                public void onSuccess(String userid) {
//                    Log.d(TAG, "--onSuccess" + userid);
////                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
////                    finish();
//                }
//
//                /**
//                 * 连接融云失败
//                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
//                 */
//                @Override
//                public void onError(RongIMClient.ErrorCode errorCode) {
//                    Log.d(TAG, "--onError" + errorCode);
//                }
//            });
////        }
//    }

}
