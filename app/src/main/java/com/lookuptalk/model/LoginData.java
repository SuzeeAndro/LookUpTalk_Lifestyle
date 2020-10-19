
package com.lookuptalk.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginData {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("errorCode")
    @Expose
    private Integer errorCode;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("info")
    @Expose
    private Info info;

    /**
     * No args constructor for use in serialization
     * 
     */
    public LoginData() {
    }

    /**
     * 
     * @param success
     * @param errorCode
     * @param description
     * @param info
     */
    public LoginData(Boolean success, Integer errorCode, String description, Info info) {
        super();
        this.success = success;
        this.errorCode = errorCode;
        this.description = description;
        this.info = info;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

}
