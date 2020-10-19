
package com.lookuptalk.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageLinks {

    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ImageLinks() {
    }

    /**
     * 
     * @param thumbnail
     */
    public ImageLinks(String thumbnail) {
        super();
        this.thumbnail = thumbnail;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

}
