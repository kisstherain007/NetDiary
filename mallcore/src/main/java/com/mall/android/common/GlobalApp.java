package com.mall.android.common;

import android.app.Application;

import com.mall.android.common.utils.imageloader.PicassoHelper;

/**
 * Created by zhoubo on 2017/2/18.
 */

public class GlobalApp extends Application {

    private static GlobalApp mGlobalApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mGlobalApp = this;

//        Stetho.initialize(
//                Stetho.newInitializerBuilder(this)
//                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
//                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
//                        .build());
        PicassoHelper.init(this);
    }

    public static GlobalApp getInstance(){
        return mGlobalApp;
    }
}
