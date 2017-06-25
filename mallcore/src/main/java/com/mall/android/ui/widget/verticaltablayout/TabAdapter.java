package com.mall.android.ui.widget.verticaltablayout;


/**
 * @author chqiu
 *         Email:qstumn@163.com
 */
public interface TabAdapter {
    int getCount();

    TabView.TabBadge getBadge(int position);

    TabView.TabIcon getIcon(int position);

    TabView.TabTitle getTitle(int position);

    int getBackground(int position);
}
