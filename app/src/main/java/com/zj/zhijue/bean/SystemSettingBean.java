package com.zj.zhijue.bean;

public class SystemSettingBean {
    private int itemPrefixImageResourceId;
    private String itemTilte;
    private int itemSufixImageResource;
    private String currentVersion;
    private String newVersion;
    private boolean haveNewVersion;

    public int getItemPrefixImageResourceId() {
        return itemPrefixImageResourceId;
    }

    public void setItemPrefixImageResourceId(int itemPrefixImageResourceId) {
        this.itemPrefixImageResourceId = itemPrefixImageResourceId;
    }

    public String getItemTilte() {
        return itemTilte;
    }

    public void setItemTilte(String itemTilte) {
        this.itemTilte = itemTilte;
    }

    public int getItemSufixImageResource() {
        return itemSufixImageResource;
    }

    public void setItemSufixImageResource(int itemSufixImageResource) {
        this.itemSufixImageResource = itemSufixImageResource;
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(String currentVersion) {
        this.currentVersion = currentVersion;
    }

    public String getNewVersion() {
        return newVersion;
    }

    public void setNewVersion(String newVersion) {
        this.newVersion = newVersion;
    }

    public boolean isHaveNewVersion() {
        return haveNewVersion;
    }

    public void setHaveNewVersion(boolean haveNewVersion) {
        this.haveNewVersion = haveNewVersion;
    }

    @Override
    public String toString() {
        return "SystemSettingBean{" +
                "itemPrefixImageResourceId=" + itemPrefixImageResourceId +
                ", itemTilte='" + itemTilte + '\'' +
                ", itemSufixImageResource=" + itemSufixImageResource +
                ", currentVersion='" + currentVersion + '\'' +
                ", newVersion='" + newVersion + '\'' +
                ", haveNewVersion=" + haveNewVersion +
                '}';
    }
}
