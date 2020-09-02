package com.zj.zhijue.bean;

import java.util.List;


public class FunctionCategorySubInfoBean extends BaseBean {

    private String categoryName;
    private int index;

    private List<FunctionCategorySubInfoBean.DataBean> data;

    public List<FunctionCategorySubInfoBean.DataBean> getData() {
        return data;
    }

    public void setData(List<FunctionCategorySubInfoBean.DataBean> data) {
        this.data = data;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public class DataBean {
        private String name;
        private int resourceId;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getResourceId() {
            return resourceId;
        }

        public void setResourceId(int resourceId) {
            this.resourceId = resourceId;
        }
    }
}
