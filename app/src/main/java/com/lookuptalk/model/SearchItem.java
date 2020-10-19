package com.lookuptalk.model;

import com.lookuptalk.model.SubItem;

import java.util.List;

public class SearchItem {
    private String itemTitle;
    private List<SubItem> subItemList;

    public SearchItem() {
    }

    public SearchItem(String itemTitle, List<SubItem> subItemList) {
        this.itemTitle = itemTitle;
        this.subItemList = subItemList;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public List<SubItem> getSubItemList() {
        return subItemList;
    }

    public void setSubItemList(List<SubItem> subItemList) {
        this.subItemList = subItemList;
    }
}
