package com.example.wasabee.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

data class Chat(
    @SerializedName("chatName")
    @Expose
    var chatName: String,

    @SerializedName("message")
    @Expose
    var lastMessage: String,

    @SerializedName("date")
    @Expose
    var date: Date,

    @SerializedName("username")
    @Expose
    var senderID: String

)