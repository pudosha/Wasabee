package spb.summer_practice.wasabee.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserData(
    @SerializedName("token")
    @Expose
    var token: String? = null,

    @SerializedName("username")
    @Expose
    var username: String? = null

)