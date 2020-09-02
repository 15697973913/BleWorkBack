package com.zj.zhijue.bean;



import com.zj.zhijue.listener.IPagerTitle;

import java.io.Serializable;

public class CatsItemBean implements IPagerTitle, Serializable {

    private String defaultFontColor;

    private ImageBean defaultIcon;
    private int id;
    private String name;
    private int selected;

    private ImageBean selectedBg;

    private String selectedBgColor;

    private String selectedFontColor;

    private ImageBean selectedIcon;

    private String shortName;
    private String type;

    public CatsItemBean(String name) {
        this.name = name;
    }

    public String getDefaultFontColor() {
        return defaultFontColor;
    }

    public void setDefaultFontColor(String defaultFontColor) {
        this.defaultFontColor = defaultFontColor;
    }

    public ImageBean getDefaultIcon() {
        return defaultIcon;
    }

    public void setDefaultIcon(ImageBean defaultIcon) {
        this.defaultIcon = defaultIcon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public ImageBean getSelectedBg() {
        return selectedBg;
    }

    public void setSelectedBg(ImageBean selectedBg) {
        this.selectedBg = selectedBg;
    }

    public String getSelectedBgColor() {
        return selectedBgColor;
    }

    public void setSelectedBgColor(String selectedBgColor) {
        this.selectedBgColor = selectedBgColor;
    }

    public String getSelectedFontColor() {
        return selectedFontColor;
    }

    public void setSelectedFontColor(String selectedFontColor) {
        this.selectedFontColor = selectedFontColor;
    }

    public ImageBean getSelectedIcon() {
        return selectedIcon;
    }

    public void setSelectedIcon(ImageBean selectedIcon) {
        this.selectedIcon = selectedIcon;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getTitle() {
        return null;
    }
}
