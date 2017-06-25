package com.mall.android.common.utils.imageloader;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.mall.android.R;
import com.mall.android.common.GlobalApp;
import com.mall.android.common.utils.Logger;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import static android.content.Context.ACTIVITY_SERVICE;
import static android.content.pm.ApplicationInfo.FLAG_LARGE_HEAP;
import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.HONEYCOMB;

/**
 * Created by zhoubo on 2017/3/5.
 */

public class PicassoHelper {

    private static final String TAG = PicassoHelper.class.getSimpleName();

    Context mContext;

    static volatile PicassoHelper singleton = null;

    private static Picasso picasso;

    LruCache picassoCache;

    public PicassoHelper(Context context){
        this.mContext = context;
        Picasso.Builder builder = new Picasso.Builder(context);
        picassoCache = new LruCache(calculateMemoryCacheSize());
        builder.memoryCache(picassoCache);
        Picasso.setSingletonInstance(builder.build());
        picasso = builder.build();
    }

    public static void init(Context context) {
        if (singleton == null) {
            synchronized (PicassoHelper.class) {
                if (singleton == null) {
                    singleton = new PicassoHelper(context.getApplicationContext());
                }
            }
        }
    }

    public void clearCache(){
        if (picassoCache != null) picassoCache.clear();
    }

    /**
     * 加载有默认图片
     * @param path
     * @param resId
     * @param imageView
     */
    public void loadImageViewHolder(Context mContext, String path, int resId, ImageView imageView) {
        picasso.with(mContext)
                .load(path)
                .fit()
//                .memoryPolicy(NO_CACHE, NO_STORE) // 此选项可节省很多内存
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .config(Bitmap.Config.RGB_565)
                .placeholder(resId)
                .into(imageView);
    }

    public void loadCircleImageViewHolder(Context mContext, String path, int resId, ImageView imageView) {
        picasso.with(mContext)
                .load(path)
                .fit()
//                .memoryPolicy(NO_CACHE, NO_STORE) // 此选项可节省很多内存
                .networkPolicy(NetworkPolicy.NO_CACHE)
//                .config(Bitmap.Config.RGB_565)
                .placeholder(resId)
                .transform(new RangleTransForm(20))
                .into(imageView);
    }


    /**
     * 加载有默认图片
     * @param path
     * @param imageView
     */
    public void loadImageViewHolder(String path, ImageView imageView) {
        loadImageViewHolder(mContext, path, R.drawable.default_bg, imageView);
    }

    public void loadCircleImageViewHolder(String path, ImageView imageView) {
        loadCircleImageViewHolder(mContext, path, R.drawable.default_bg, imageView);
    }

    public void loadImageViewHolder(Context context, String path, ImageView imageView) {
        loadImageViewHolder(context, path, R.drawable.default_bg, imageView);
    }

    public Picasso getPicasso() {
        return picasso;
    }

    public static PicassoHelper getSingleton() {
        return singleton;
    }

    public void SystemoutPicassoLruCacheInfo(){
        Logger.d(TAG, " picassoCache.maxSize() : " +  picassoCache.maxSize());
        Logger.d(TAG, " picassoCache.size() : " +  picassoCache.size());
    }

    public int calculateMemoryCacheSize() {
        ActivityManager am = (ActivityManager) GlobalApp.getInstance().getSystemService(ACTIVITY_SERVICE);
        boolean largeHeap = (mContext.getApplicationInfo().flags & FLAG_LARGE_HEAP) != 0;
        int memoryClass = am.getMemoryClass();
        if (largeHeap && SDK_INT >= HONEYCOMB) {
            memoryClass = am.getLargeMemoryClass();
        }
        return 1024 * 1024 * memoryClass / 20;
    }
}
