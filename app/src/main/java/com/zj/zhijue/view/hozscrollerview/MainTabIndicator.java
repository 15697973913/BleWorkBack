package com.zj.zhijue.view.hozscrollerview;

import android.content.Context;
import android.util.AttributeSet;

import com.zj.zhijue.R;
import com.zj.zhijue.bean.CatsItemBean;

import java.util.ArrayList;
import java.util.List;


public class MainTabIndicator extends  ImageTabIndicator {
    private final int DEFAULT_BACKGROUND_RESOURCE_ID = R.drawable.navigation_bg;
    private final String DEFAULT_FONT_COLOR = "#374CFF";
    private final String DEFAULT_SELECTED_COLOR = "#ffffff";

    public MainTabIndicator(Context context) {
        super(context);
    }

    public MainTabIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        setDefaultBackground(this.DEFAULT_BACKGROUND_RESOURCE_ID);
    }

    public void updateData(List<CatsItemBean> list) {
        updateData(list, -1);
    }

    public void updateData(List<CatsItemBean> list, int selectedIndex) {
        if (list == null || list.size() == 0) {
            clearViews();
            return;
        }
        ArrayList<IndicatorItemBean> desList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            CatsItemBean item = list.get(i);
            IndicatorItemBean desItem = new IndicatorItemBean();
            desItem.setTitle(item.getName());
            desItem.setDefaultFontColor(DEFAULT_FONT_COLOR);
            desItem.setSelectedFontColor(DEFAULT_SELECTED_COLOR);
            desList.add(desItem);
        }
        updateData(desList, selectedIndex == -1 ? 0 : selectedIndex);
    }

}
