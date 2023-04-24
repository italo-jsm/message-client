package model

import common.InstantSerializer
import java.time.Instant
import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val payload: String,
    val senderId: String,
    val receiverId: String,
)