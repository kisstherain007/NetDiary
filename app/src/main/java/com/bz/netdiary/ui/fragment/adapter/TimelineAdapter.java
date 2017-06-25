package com.bz.netdiary.ui.fragment.adapter;

import com.bz.netdiary.R;
import com.bz.netdiary.ui.weight.DisplayPicsView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by zhoubo on 2017/3/2.
 */

public class TimelineAdapter extends BaseQuickAdapter<FriendEntity, BaseViewHolder> {

    public TimelineAdapter(List<FriendEntity> datas){
        super(R.layout.timeline_item_lay, datas);
    }

    public TimelineAdapter(int layoutResId, List<FriendEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FriendEntity item) {

        DisplayPicsView displayPicsView = helper.getView(R.id.display_view);
        displayPicsView.setPics(null, item.getImageurls());
        helper.setText(R.id.content_textView, item.getContent());
//        PicassoHelper.getSingleton().loadImageViewHolder(item.imgs, (ImageView) helper.getView(R.id.product_imageView));
    }
}
