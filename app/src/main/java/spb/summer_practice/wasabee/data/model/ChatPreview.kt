package spb.summer_practice.wasabee.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ChatPreview(
    @SerializedName("username")
    @Expose
    var username: String,

    @SerializedName("message")
    @Expose
    var message: String,

    @SerializedName("date")
    @Expose
    var date: java.util.Date,

    @SerializedName("chat")
    @Expose
    var chat: Chat
)