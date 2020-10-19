package com.lookuptalk.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImdbFavModel {


    @SerializedName("_id")
    @Expose
    private String _id;


    /**
     * No args constructor for use in serialization
     */
    public ImdbFavModel() {
    }


    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }


}