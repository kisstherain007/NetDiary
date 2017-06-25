package com.mall.android.common.utils.imageloader;

import android.content.Context;

import com.squareup.picasso.LruCache;

/**
 * Created by zhoubo on 2017/3/8.
 */

public class PicassoCache extends LruCache {

    public PicassoCache(Context context) {
        super(context);
    }

    public PicassoCache(int maxSize) {
        super(maxSize);
    }

}
