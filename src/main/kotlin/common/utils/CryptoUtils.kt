package common.utils

import service.getCredentials
import java.security.*
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*
import javax.crypto.Cipher

class CryptoUtils {
    companion object{
        fun encryptMessage(plainMessage: String, destinationPublicKey: String) : String{
            val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
            cipher.init(Cipher.ENCRYPT_MODE, loadPublicKey(destinationPublicKey))
            return Base64.getEncoder().encodeToString(cipher.doFinal
                (plainMessage.toByteArray()))
        }

        fun generateKeyPair(): KeyPair {
            val generator = KeyPairGenerator.getInstance("RSA")
            generator.initialize(2048, SecureRandom())
            return generator.genKeyPair()
        }

        private fun loadPublicKey(stored: String): Key {
            val data: ByteArray = Base64.getDecoder().
            decode(stored.toByteArray())
            val spec = X509EncodedKeySpec(data)
            val fact = KeyFactory.getInstance("RSA")
            return fact.generatePublic(spec)
        }

        fun decryptMessage(encryptedText: String?):
                String {
            val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
            cipher.init(Cipher.DECRYPT_MODE, loadPrivateKey(getCredentials().userPrivateKey))
            return String(cipher.doFinal(
                Base64.getDecoder().
                decode(encryptedText)))
        }

        fun loadPrivateKey(key64: String): PrivateKey {
            val clear: ByteArray = Base64.getDecoder().
            decode(key64.toByteArray())
            val keySpec = PKCS8EncodedKeySpec(clear)
            val fact = KeyFactory.getInstance("RSA")
            val priv = fact.generatePrivate(keySpec)
            Arrays.fill(clear, 0.toByte())
            return priv
        }
    }
}
