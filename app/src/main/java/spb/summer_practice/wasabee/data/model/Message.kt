package spb.summer_practice.wasabee.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Message(
    @SerializedName("messageID")
    @Expose
    var messageID: String,

    @SerializedName("username")
    @Expose
    var username: String,

    @SerializedName("chatID")
    @Expose
    var chatID: String,

    @SerializedName("message")
    @Expose
    var message: String,

    @SerializedName("date")
    @Expose
    var date: java.util.Date,

    @SerializedName("isEdited")
    @Expose
    var isEdited: Boolean
)