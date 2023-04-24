package service

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.jsonBody
import common.utils.CryptoUtils
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import model.User
import java.io.File
import java.util.*

fun createUser(username: String, email: String) {
    val keyPair = CryptoUtils.generateKeyPair()
    File("userCredentials.json").writeText(Json.encodeToString(UserCredentials(
        userId = Fuel.post("http://localhost:8080/users")
            .jsonBody(Json.encodeToString(User(
                id= "",
                username = username,
                email = email,
                publicKey = Base64.getEncoder().encodeToString(keyPair.public.encoded)
            )))
            .response()
            .second.body().asString("text/plain"),
        userPrivateKey = Base64.getEncoder().encodeToString(keyPair.private.encoded)
    )))
}

fun getCredentials() =
    Json.decodeFromString<UserCredentials>(File("userCredentials.json").readText())
