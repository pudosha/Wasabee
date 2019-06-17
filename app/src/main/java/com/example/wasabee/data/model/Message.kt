package com.example.wasabee.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Message(
    @SerializedName("message")
    @Expose
    var message: String,

    @SerializedName("date")
    @Expose
    var date: String,

    @SerializedName("sender")
    @Expose
    var senderID: String

)