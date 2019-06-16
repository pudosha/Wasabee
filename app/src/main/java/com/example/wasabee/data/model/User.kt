package com.example.wasabee.data.model

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class User(
    val userId: String,
    val firstName: String,
    val lastName: String
)
