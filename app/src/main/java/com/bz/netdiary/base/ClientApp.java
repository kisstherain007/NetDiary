package com.bz.netdiary.base;

import com.mall.android.common.GlobalApp;

import io.rong.imkit.RongIM;


/**
 * Created by zhoubo on 2017/2/17.
 */
public class ClientApp extends GlobalApp {

    @Override
    public void onCreate() {
        super.onCreate();
        /**初始化融云*/
        RongIM.init(this);
    }
}

