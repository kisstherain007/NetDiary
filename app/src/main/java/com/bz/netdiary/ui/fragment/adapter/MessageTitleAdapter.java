package com.bz.netdiary.ui.fragment.adapter;

import com.bz.netdiary.R;
import com.bz.netdiary.ui.weight.DisplayPicsView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by zhoubo on 2017/3/2.
 */

public class MessageTitleAdapter extends BaseQuickAdapter<FriendEntity, BaseViewHolder> {

    public MessageTitleAdapter(List<FriendEntity> datas){
        super(R.layout.message_title_lay, datas);
    }

    public MessageTitleAdapter(int layoutResId, List<FriendEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FriendEntity item) {

//        PicassoHelper.getSingleton().loadImageViewHolder(item.imgs, (ImageView) helper.getView(R.id.product_imageView));
    }
}
