package service

import kotlinx.serialization.Serializable

@Serializable
data class UserCredentials(
    val userId: String,
    val userPrivateKey: String
)