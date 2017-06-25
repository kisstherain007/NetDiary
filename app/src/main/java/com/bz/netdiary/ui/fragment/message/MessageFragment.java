package com.bz.netdiary.ui.fragment.message;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bz.netdiary.R;
import com.bz.netdiary.ui.fragment.adapter.FriendEntity;
import com.bz.netdiary.ui.fragment.adapter.MessageTitleAdapter;
import com.bz.netdiary.ui.fragment.adapter.TimelineAdapter;
import com.bz.netdiary.ui.fragment.base.AbstractTableViewFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.mall.android.ui.fragment.BaseFragment;

import java.util.ArrayList;

/**
 * Created by zhoubo on 2017/5/15.
 */

public class MessageFragment extends AbstractTableViewFragment {

    TextView titleView;

    public static MessageFragment newInstance() {
        MessageFragment fragment = new MessageFragment();
        return fragment;
    }

    @Override
    public int inflateContentView() {
        return R.layout.message_lay;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        titleView = (TextView) view.findViewById(R.id.toolbar_title);
        titleView.setText("消息");

        getRecyclerView().addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
    }

    @Override
    protected int tableViewDisplayType() {
        return TABLEVIEW_LIST_TYPE;
    }

    @Override
    public BaseQuickAdapter configRecyclerViewAdapter() {
        ArrayList<FriendEntity> list = new ArrayList<>();
        for (int index = 0; index < 20; index++){
            list.add(new FriendEntity());
        }
        return new MessageTitleAdapter(list);
    }
}
