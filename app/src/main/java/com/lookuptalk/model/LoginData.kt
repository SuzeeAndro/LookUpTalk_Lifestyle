package com.lookuptalk

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginResponse {
    @SerializedName("success")
    @Expose
    var success: Boolean? = null

    @SerializedName("errorCode")
    @Expose
    var errorCode: Int? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("info")
    @Expose
    var info: Any? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param success
     * @param errorCode
     * @param description
     * @param info
     */
    constructor(
        success: Boolean?,
        errorCode: Int?,
        description: String?,
        info: Any?
    ) : super() {
        this.success = success
        this.errorCode = errorCode
        this.description = description
        this.info = info
    }

}