package com.bz.netdiary.ui.fragment.friend;

import android.os.Bundle;
import android.view.View;

import com.bz.netdiary.R;
import com.bz.netdiary.ui.fragment.base.AbstractTableViewFragment;
import com.bz.netdiary.ui.fragment.adapter.TimelineAdapter;
import com.bz.netdiary.ui.fragment.adapter.FriendEntity;
import com.bz.netdiary.ui.fragment.adapter.Images;
import com.bz.netdiary.ui.weight.Imageurls;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoubo on 2017/5/17.
 */

public class WebDiaryFragment extends AbstractTableViewFragment {

    List<FriendEntity> friendEntityList = new ArrayList<>();

    public static WebDiaryFragment newInstance() {
        WebDiaryFragment fragment = new WebDiaryFragment();
        return fragment;
    }

    @Override
    protected int tableViewDisplayType() {
        return TABLEVIEW_LIST_TYPE;
    }

    @Override
    public BaseQuickAdapter configRecyclerViewAdapter() {
        return new TimelineAdapter(friendEntityList);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initData();
    }

    private void initData(){

        for (int i = 0; i < 100; i++){

            FriendEntity friendEntity = new FriendEntity();
            friendEntity.setOtherlayout(1);
            friendEntity.setImages(R.mipmap.ic_launcher);
            friendEntity.setContentimages(R.mipmap.ic_launcher);
            friendEntity.setName("周润发" + i);
            friendEntity.setContent("这个我拍摄的风景图片如何O(∩_∩)O~，大家快来赞吧，喜欢的可以收藏哦~~~~~");
            friendEntity.setDate("29分钟前");
            friendEntity.setPraise("涛涛，淦成，瑞瑞，重重");
            if (i % 2 == 0)
                friendEntity.setCommentary("不错啊!");

            List<Imageurls> list = new ArrayList<>();
            java.util.Random random = new java.util.Random();
            int imageCount = random.nextInt(8);
            for (int index = 0; index < imageCount; index++){
                Imageurls imageurls = new Imageurls();
                java.util.Random random1 = new java.util.Random();
                int position = random1.nextInt(Images.imageThumbUrls.length - 1);
                imageurls.setUrl(Images.imageThumbUrls[position]);
                list.add(imageurls);
            }
            friendEntity.setImageurls(list);

            friendEntityList.add(friendEntity);
        }
    }
}
