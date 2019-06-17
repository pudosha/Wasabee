package com.example.wasabee.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class User(
    @SerializedName("userId")
    @Expose
    val userId: String,

    @SerializedName("firstName")
    @Expose
    val firstName: String,

    @SerializedName("userId")
    @Expose
    val lastName: String
)