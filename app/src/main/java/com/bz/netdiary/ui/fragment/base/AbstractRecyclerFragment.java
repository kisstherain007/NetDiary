package com.bz.netdiary.ui.fragment.base;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bz.netdiary.R;
import com.bz.netdiary.base.ClientApp;
import com.bz.netdiary.ui.weight.MallLoadMoreView;
import com.bz.netdiary.ui.weight.SpaceItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mall.android.common.utils.Logger;
import com.mall.android.common.utils.MemoryUtil;
import com.mall.android.common.utils.imageloader.PicassoHelper;
import com.mall.android.ui.fragment.BaseFragment;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

/**
 * Created by zhoubo on 2017/3/2.
 */

public abstract class AbstractRecyclerFragment extends BaseFragment {

    private static final String TAG = AbstractRecyclerFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private BaseQuickAdapter baseQuickAdapter;

    private View notDataView;
    private View errorView;

    @Override
    protected int inflateContentView() {
        return R.layout.comm_ui_recyclerview;
    }

    private Map<String, WeakReference<View>> viewCache = new HashMap<>();

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        baseQuickAdapter = configRecyclerViewAdapter();
        mRecyclerView.setLayoutManager(configRecyclerViewLayoutManager()); // 配置适配器

        /************************分割线************************/
        RecyclerView.ItemDecoration itemDecoration = configDividerItemDecoration();
        if (itemDecoration == null){
            itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        }
        mRecyclerView.addItemDecoration(itemDecoration); // 分割线

//        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin_small); // 分割间距
//        mRecyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));
        /************************************************/

//        mRecyclerView.setItemViewCacheSize(5);
        baseQuickAdapter.setLoadMoreView(new MallLoadMoreView());
        mRecyclerView.setAdapter(baseQuickAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerScollListener());
        mRecyclerView.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(RecyclerView.ViewHolder holder) {
//                Logger.d(TAG, holder.toString());
                if (holder instanceof BaseViewHolder){
                    if (!viewCache.containsKey(holder.itemView.toString())) {
//                        Log.d(TAG, holder.itemView.toString() + "保存一个View到Cache");
                        viewCache.put(holder.itemView.toString(), new WeakReference<View>(holder.itemView));
                    }
                }
            }
        });

        notDataView = getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) mRecyclerView.getParent(), false);
        errorView = getLayoutInflater().inflate(R.layout.error_view, (ViewGroup) mRecyclerView.getParent(), false);
    }

    public void setRecycledViewPool(RecyclerView.RecycledViewPool pool ){
        if (pool != null){
            getRecyclerView().setRecycledViewPool(pool);
        }
    }

    /**
     * RecyclerView 滑动监听
     */
    class RecyclerScollListener extends RecyclerView.OnScrollListener{

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            Logger.d(TAG, "onScrollStateChanged newState: " + newState);
            switch (newState){
                case SCROLL_STATE_IDLE: // The RecyclerView is not currently scrolling.
//                    Logger.d("RecyclerScollListener", "not scrolling");
                    PicassoHelper.getSingleton().getPicasso().resumeTag(ClientApp.getInstance());
                    break;
//                case SCROLL_STATE_DRAGGING: // The RecyclerView is currently being dragged by outside input such as user touch input.
//                    ClientApp.getInstance().getPicassoHelper().getPicasso().pauseTag(ClientApp.getInstance());
//                    Logger.d("RecyclerScollListener", "scrolling...");
//                    break;
//                case SCROLL_STATE_SETTLING: // The RecyclerView is currently animating to a final position while not under
//                    ClientApp.getInstance().getPicassoHelper().getPicasso().pauseTag(ClientApp.getInstance());
//                    Logger.d("RecyclerScollListener", "scrolling...");
//                    break;
                default:
//                    Logger.d(TAG, "memory: " + memoryUtil.getCurrentMemorySize());
//                    Logger.d(TAG, "PicassoHelper : " + memoryUtil.getCurrentMemorySize());
                    Logger.d(TAG, "calculateMemoryCacheSize : " + (PicassoHelper.getSingleton().calculateMemoryCacheSize() / (1024*1024)));
                    PicassoHelper.getSingleton().getPicasso().pauseTag(ClientApp.getInstance());
                break;
            }
        }
    }

    MemoryUtil memoryUtil = new MemoryUtil();

    @Override
    public void requestData() {
        super.requestData();
    }

    public abstract LinearLayoutManager configRecyclerViewLayoutManager();
    public abstract BaseQuickAdapter configRecyclerViewAdapter();

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public BaseQuickAdapter getRecyclerAdapter() {
        return baseQuickAdapter;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach()");
//        PicassoHelper.getSingleton().clearCache(); // 清理所有图片缓存
        getRecyclerView().getRecycledViewPool().clear();
    }


    /****************************释放ImageView*******************************/
    Handler mHandler = new Handler();
    Runnable releaseRunnable = new Runnable() {
        @Override
        public void run() {
            releaseImageViewByIds();
        }
    };

    private void releaseImageViewByIds(){
        if (getRecyclerView() != null){
            int childSize = getRecyclerView().getChildCount();
            for (int i = 0; i < childSize; i++) {
                View view = getRecyclerView().getChildAt(i);
                releaseImageView(view);
                if (viewCache.containsKey(view.toString())) {
//                    Logger.d(TAG, "已经释放了，从Cache中移除");
                    viewCache.remove(view.toString());
                }
            }
            if (viewCache.size() > 0) {
                Set<String> keySet = viewCache.keySet();
                for (String key : keySet) {
                    View view = viewCache.get(key).get();
                    if (view != null) {
//                        Logger.d(TAG, "从Cache中释放一个View");
                        releaseImageView(view);
                    }
                }
                viewCache.clear();
            }
        }
    }

    private void releaseImageView(View parentView){
        if (configCanReleaseIds() != null) {
            for (int imgId : configCanReleaseIds()) {
                ImageView imgView = (ImageView) parentView.findViewById(imgId);
                if (imgView != null) {
                    imgView.setImageDrawable(null);
//                    Logger.d(TAG, "释放ImageView");
                }
            }
        }
    }

    public void releaseImageViewResouce(ImageView imageView) {
        if (imageView == null) return;
        Drawable drawable = imageView.getDrawable();
        if (drawable != null && drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
    }

    @Override
    public void onPause() {
        Logger.d(TAG, "onPause()");
        super.onPause();
        mHandler.postDelayed(releaseRunnable, 1 * 1000);
    }

    @Override
    public void onResume() {
        Logger.d(TAG, "onResume()");
        super.onResume();
        mHandler.removeCallbacks(releaseRunnable);
        getRecyclerAdapter().notifyDataSetChanged();
    }

//    product_imageView
    protected int[] configCanReleaseIds() {
        return null;
    }

    public RecyclerView.ItemDecoration configDividerItemDecoration(){
        return null;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
