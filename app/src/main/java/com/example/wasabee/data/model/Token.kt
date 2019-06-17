package com.example.wasabee.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Token(
    @SerializedName("success")
    @Expose
    var success: Boolean,

    @SerializedName("message")
    @Expose
    var message: String,

    @SerializedName("token")
    @Expose
    var token: String? = null
)