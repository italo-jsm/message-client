package service

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.jsonBody
import common.utils.CryptoUtils
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import model.Message
import model.User

fun sendMessage(messagePayload: String, destinationId: String){
    val message = Message(
        payload = CryptoUtils.encryptMessage(messagePayload, getDestinationUser(destinationId).publicKey),
        senderId = getCredentials().userId,
        receiverId = destinationId
    )

    Fuel.post("http://localhost:8080/messages")
        .jsonBody(Json.encodeToString(message))
        .response()
}

fun receiveAllMessages(userId: String): User =
    Json.decodeFromString(
        Fuel.get("http://localhost:8080/users?userId=$userId")
            .response()
            .second
            .body().asString("application/json")
    )

fun getDestinationUser(userId: String) : User =
    Json.decodeFromString(Fuel.get("http://localhost:8080/users?userId=$userId")
        .response()
        .second
        .body().asString("application/json"))
