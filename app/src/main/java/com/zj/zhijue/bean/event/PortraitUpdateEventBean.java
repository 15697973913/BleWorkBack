package com.zj.zhijue.bean.event;

public class PortraitUpdateEventBean {
    public boolean portraitImageUpdate;
    public String newImageUrl;

    public PortraitUpdateEventBean(boolean portraitImageUpdate, String newImageUrl) {
        this.portraitImageUpdate = portraitImageUpdate;
        this.newImageUrl = newImageUrl;
    }
}
