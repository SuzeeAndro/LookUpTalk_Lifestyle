
package com.lookuptalk.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VolumeInfo {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("subtitle")
    @Expose
    private String subtitle;
    @SerializedName("imageLinks")
    @Expose
    private ImageLinks imageLinks;

    /**
     * No args constructor for use in serialization
     * 
     */
    public VolumeInfo() {
    }

    /**
     * 
     * @param subtitle
     * @param title
     * @param imageLinks
     */
    public VolumeInfo(String title, String subtitle, ImageLinks imageLinks) {
        super();
        this.title = title;
        this.subtitle = subtitle;
        this.imageLinks = imageLinks;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public ImageLinks getImageLinks() {
        return imageLinks;
    }

    public void setImageLinks(ImageLinks imageLinks) {
        this.imageLinks = imageLinks;
    }

}
