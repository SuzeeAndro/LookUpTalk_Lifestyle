package com.lookuptalk.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImdbModel {

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    @SerializedName("profile_path")
    @Expose
    private String profile_path;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @SerializedName("isSelected")
    @Expose
    private boolean isSelected = false;



    /**
     * No args constructor for use in serialization
     *
     */
    public ImdbModel() {
    }

    /**
     *
     * @param title
     * @param posterPath
     */
    public ImdbModel(String title, String posterPath) {
        super();
        this.title = title;
        this.posterPath = posterPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

  public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

}