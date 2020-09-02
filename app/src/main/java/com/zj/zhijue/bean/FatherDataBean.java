package com.zj.zhijue.bean;

import java.util.ArrayList;

/**
 * 一级列表数据类
 */
public class FatherDataBean {
    private String title;
    private ArrayList<ChildrenDataBean> list;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<ChildrenDataBean> getList() {
        return list;
    }

    public void setList(ArrayList<ChildrenDataBean> list) {
        this.list = list;
    }
}
