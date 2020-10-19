package com.lookuptalk.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GoogleBooks {

@SerializedName("kind")
@Expose
private String kind;
@SerializedName("id")
@Expose
private String id;
@SerializedName("etag")
@Expose
private String etag;
@SerializedName("selfLink")

@Expose
private String selfLink;
@SerializedName("volumeInfo")
@Expose
private VolumeInfo volumeInfo;

    @SerializedName("isSelected")
    @Expose
    private boolean isSelected = false;
    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    /**
* No args constructor for use in serialization
*
*/
public GoogleBooks() {
}

/**
*
* @param kind
* @param volumeInfo
* @param etag
* @param id
* @param selfLink
*/
public GoogleBooks(String kind, String id, String etag, String selfLink, VolumeInfo volumeInfo) {
super();
this.kind = kind;
this.id = id;
this.etag = etag;
this.selfLink = selfLink;
this.volumeInfo = volumeInfo;
}

public String getKind() {
return kind;
}

public void setKind(String kind) {
this.kind = kind;
}

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getEtag() {
return etag;
}

public void setEtag(String etag) {
this.etag = etag;
}

public String getSelfLink() {
return selfLink;
}

public void setSelfLink(String selfLink) {
this.selfLink = selfLink;
}

public VolumeInfo getVolumeInfo() {
return volumeInfo;
}

public void setVolumeInfo(VolumeInfo volumeInfo) {
this.volumeInfo = volumeInfo;
}

}