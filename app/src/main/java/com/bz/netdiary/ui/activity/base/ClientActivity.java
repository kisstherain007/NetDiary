package com.bz.netdiary.ui.activity.base;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.WindowManager;

import com.bz.netdiary.R;
import com.bz.netdiary.ui.fragment.friend.FriendDiaryFragment;
import com.bz.netdiary.utils.ClientUtil;
import com.mall.android.ui.activity.BaseActivity;
import com.mall.android.ui.fragment.BaseFragment;

/**
 * Created by zhoubo on 2017/5/17.
 */

public class ClientActivity extends BaseActivity {

    BaseFragment currentFragment;

    public static void newInstanceShopList(Activity fromActivity, int type){
        Intent intent = new Intent(fromActivity, ClientActivity.class);
        intent.putExtra("type", type);
        fromActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ClientUtil.setStatusBar(this);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//此FLAG可使状态栏透明，且当前视图在绘制时，从屏幕顶端开始即top = 0开始绘制，这也是实现沉浸效果的基础
//            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//可不加
//        }
        setContentView(R.layout.activity_client);

        Intent intent = getIntent();
        int type = intent.getIntExtra("type", 0);

        switch (type){
            case 0:
                findViewById(R.id.toolbar).setVisibility(View.GONE);
                currentFragment = FriendDiaryFragment.newInstance();
                break;
        }

        if (currentFragment != null)
            getSupportFragmentManager().beginTransaction().add(R.id.container_lay, currentFragment, "BaseFragment").commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (currentFragment != null)
            getSupportFragmentManager().beginTransaction().remove(currentFragment);
    }

    private void full(boolean enable) {
        if (enable) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(lp);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            WindowManager.LayoutParams attr = getWindow().getAttributes();
            attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attr);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }
}
