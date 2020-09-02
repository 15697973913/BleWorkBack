package com.zj.zhijue.bean;

public class GuardianBean {
    private int porttraitImageId;//默认头像图片资源ID
    private String portraitImageUrl;//自定义头像
    private String account;
    private String relationShip;//关系

    public int getPorttraitImageId() {
        return porttraitImageId;
    }

    public void setPorttraitImageId(int porttraitImageId) {
        this.porttraitImageId = porttraitImageId;
    }

    public String getPortraitImageUrl() {
        return portraitImageUrl;
    }

    public void setPortraitImageUrl(String portraitImageUrl) {
        this.portraitImageUrl = portraitImageUrl;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getRelationShip() {
        return relationShip;
    }

    public void setRelationShip(String relationShip) {
        this.relationShip = relationShip;
    }
}
