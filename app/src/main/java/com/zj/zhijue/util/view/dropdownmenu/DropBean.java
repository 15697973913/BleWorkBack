package com.zj.zhijue.util.view.dropdownmenu;

public class DropBean {
    private String name;
    private int imageResourceId;

    private boolean choiced = false;

    public DropBean() {
    }

    public DropBean(String name){
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public boolean isChoiced() {
        return choiced;
    }
    public void setChoiced(boolean choiced) {
        this.choiced = choiced;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }
}
