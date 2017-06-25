package com.bz.netdiary.ui.fragment.adapter;

import android.widget.ImageView;

import com.bz.netdiary.R;
import com.bz.netdiary.ui.weight.DisplayPicsView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mall.android.common.utils.imageloader.PicassoHelper;

import java.util.List;

/**
 * Created by zhoubo on 2017/3/2.
 */

public class PhotoDiaryAdapter extends BaseQuickAdapter<PhotoDiaryEntity, BaseViewHolder> {

    public PhotoDiaryAdapter(List<PhotoDiaryEntity> datas){
        super(R.layout.photo_diary_item_lay, datas);
    }

    public PhotoDiaryAdapter(int layoutResId, List<PhotoDiaryEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PhotoDiaryEntity item) {

        PicassoHelper.getSingleton().loadImageViewHolder(item.getImageUrl(), (ImageView) helper.getView(R.id.photo_imageView));
    }
}
