package com.lookuptalk.model;

public class SubItem {
    public SubItem(String _id,String url, String name,String isSelected) {
        this._id = _id;
        this.url = url;
        this.name = name;
        this.isSelected = isSelected;
    }

    //    private int subItemImage;
    private String url;
    private boolean isSelect= false;

    public String getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(String isSelected) {
        this.isSelected = isSelected;
    }

    private String isSelected;
    private String _id;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public void setSelect(boolean select) {
        isSelect = select;
    }


    public boolean isSelect() {
        return isSelect;
    }
}
