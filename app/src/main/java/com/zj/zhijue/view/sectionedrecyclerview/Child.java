package com.zj.zhijue.view.sectionedrecyclerview;

public class Child {

    String name;
    String imageUrl;
    String searchText;
    int resourceId;

    public Child(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    @Override
    public String toString() {
        return "Child{" +
                "name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", searchText='" + searchText + '\'' +
                ", resourceId=" + resourceId +
                '}';
    }
}