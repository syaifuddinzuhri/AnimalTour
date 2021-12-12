package org.aplas.animaltour;
public class DataItem {
    private String title;
    private String info;
    private int color;
    private int icon;
    public DataItem(String mTitle, String mInfo, int mColor, int mIcon) {
        title = mTitle;
        info = mInfo;
        color = mColor;
        icon = mIcon;
    }
    public String getTitle() {
        return title;
    }
    public String getInfo() {
        return info;
    }
    public int getColor() {
        return color;
    }
    public int getIcon() {
        return icon;
    }
    public void setTitle(String val) {
        this.title =val;
    }
    public void setInfo(String val) {
        this.info = val;
    }
    public void setColor(int val) {
        this.color = val;
    }
    public void setIcon(int val) {
        this.icon = val;
    }
}