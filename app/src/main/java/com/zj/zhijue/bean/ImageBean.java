package com.zj.zhijue.bean;

import java.io.Serializable;

public class ImageBean implements Serializable {

    private int W;
    private int H;

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getW() {
        return W;
    }

    public void setW(int w) {
        W = w;
    }

    public int getH() {
        return H;
    }

    public void setH(int h) {
        H = h;
    }
}
