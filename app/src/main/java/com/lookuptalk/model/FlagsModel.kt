package com.lookuptalk.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class FlagsModel {
    @SerializedName("_id")
    @Expose
    var id: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("url")
    @Expose
    var url: String? = null

    var isSelected = false

    /**
     * No args constructor for use in serialization
     */
    constructor() {}

    /**
     * @param name
     * @param id
     * @param url
     */
    constructor(id: String?, name: String?, url: String?) : super() {
        this.id = id
        this.name = name
        this.url = url

    }

}