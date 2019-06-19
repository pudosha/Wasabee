package com.example.wasabee.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Chat(
    @SerializedName("chatName")
    @Expose
    var chatName: String,

    @SerializedName("chatID")
    @Expose
    var chatID: String,

    @SerializedName("message")
    @Expose
    var lastMessage: String,

    @SerializedName("date")
    @Expose
    var date: String,

    @SerializedName("username")
    @Expose
    var senderID: String

)