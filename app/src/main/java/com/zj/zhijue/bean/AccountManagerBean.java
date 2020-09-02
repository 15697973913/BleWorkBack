package com.zj.zhijue.bean;

public class AccountManagerBean {
    private int prefixImageResourceId;
    private String itemTitle;
    private String itemContent;
    private int sufixImageResourceId;
    private boolean isLight;//是否是亮色
    private boolean isShow;

    public int getPrefixImageResourceId() {
        return prefixImageResourceId;
    }

    public void setPrefixImageResourceId(int prefixImageResourceId) {
        this.prefixImageResourceId = prefixImageResourceId;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemContent() {
        return itemContent;
    }

    public void setItemContent(String itemContent) {
        this.itemContent = itemContent;
    }

    public int getSufixImageResourceId() {
        return sufixImageResourceId;
    }

    public void setSufixImageResourceId(int sufixImageResourceId) {
        this.sufixImageResourceId = sufixImageResourceId;
    }

    public boolean isLight() {
        return isLight;
    }

    public void setLight(boolean light) {
        isLight = light;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    @Override
    public String toString() {
        return "AccountManagerBean{" +
                "prefixImageResourceId=" + prefixImageResourceId +
                ", itemTitle='" + itemTitle + '\'' +
                ", itemContent='" + itemContent + '\'' +
                ", sufixImageResourceId=" + sufixImageResourceId +
                ", isLight=" + isLight +
                '}';
    }
}
