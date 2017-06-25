package com.mall.android.network;

import com.mall.android.common.utils.SdcardUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Created by zhoubo on 2017/2/18.
 */

public class OkHttpClientManager {

    private static final String TAG = "OkHttpClientManager";

    private static OkHttpClient sInstance;

    public static OkHttpClient getInstance() {

        if (sInstance == null) {
            synchronized (OkHttpClientManager.class) {
                if (sInstance == null) {
                    File sdcache = SdcardUtils.getExternalCacheDir();
                    int cacheSize = 10 * 1024 * 1024;
                    OkHttpClient.Builder builder = new OkHttpClient.Builder()
                            .connectTimeout(30000, TimeUnit.MILLISECONDS)
                            .readTimeout(30000, TimeUnit.MILLISECONDS)
                            .writeTimeout(30000, TimeUnit.MILLISECONDS)
                            .cache(new Cache(sdcache.getAbsoluteFile(), cacheSize));
                    sInstance = builder.build();
                }
            }
        }

        return sInstance;
    }

}
