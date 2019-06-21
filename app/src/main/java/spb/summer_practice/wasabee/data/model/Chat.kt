package spb.summer_practice.wasabee.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Chat(
    @SerializedName("chatID")
    @Expose
    var chatID: String,


    @SerializedName("chatName")
    @Expose
    var chatName: String
)