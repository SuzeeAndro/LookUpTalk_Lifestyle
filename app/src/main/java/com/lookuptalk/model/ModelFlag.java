
package com.lookuptalk.model;

public class ModelFlag {

    private String text;
    private String _id;
    private String name;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String url;
    private boolean isSelected = false;

    public ModelFlag(String text) {
      this.text = text;
    }

    public String getText() {
      return text;
    }

    public void setSelected(boolean selected) {
      isSelected = selected;
    }


    public boolean isSelected() {
      return isSelected;
    }
}