package com.bz.netdiary.ui.fragment.friend;

import android.os.Bundle;
import android.view.View;

import com.bz.netdiary.ui.fragment.adapter.Images;
import com.bz.netdiary.ui.fragment.adapter.PhotoDiaryAdapter;
import com.bz.netdiary.ui.fragment.adapter.PhotoDiaryEntity;
import com.bz.netdiary.ui.fragment.base.AbstractTableViewFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoubo on 2017/5/17.
 */

public class PhotoDiaryFragment extends AbstractTableViewFragment {

    List<PhotoDiaryEntity> list = new ArrayList<>();

    public static PhotoDiaryFragment newInstance() {
        PhotoDiaryFragment fragment = new PhotoDiaryFragment();
        return fragment;
    }

    @Override
    protected int tableViewDisplayType() {
        return TABLEVIEW_GRID_TYPE;
    }

    @Override
    public int tableViewGridSpanCount() {
        return 3;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initData();
    }

    private void initData() {

        for (int i = 0; i < 100; i++){

            PhotoDiaryEntity photoE = new PhotoDiaryEntity();
            java.util.Random random1 = new java.util.Random();
            int position = random1.nextInt(Images.imageThumbUrls.length - 1);
            photoE.setImageUrl(Images.imageThumbUrls[position]);
            list.add(photoE);
        }
    }

    @Override
    public BaseQuickAdapter configRecyclerViewAdapter() {
        return new PhotoDiaryAdapter(list);
    }
}
