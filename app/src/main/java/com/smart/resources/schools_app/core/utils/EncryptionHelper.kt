package com.smart.resources.schools_app.core.utils

import android.util.Base64
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.core.extentions.TAG
import okio.ByteString.Companion.toByteString
import java.lang.Exception
import java.nio.charset.StandardCharsets
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


class EncryptionHelper (private val encryptionKey: String, private val iv: String) {

    companion object {
        private const val ENCRYPTION_ALGORITHM = "AES"

        fun generateEncryptionKey(): String {
            val keygen = KeyGenerator.getInstance(ENCRYPTION_ALGORITHM)
            keygen.init(256)

            val key: SecretKey = keygen.generateKey()
            return key.encoded.encodedAsString
        }

        fun generateInitializationVector(): String {
            val iv = ByteArray(16)
            val random = SecureRandom()
            random.nextBytes(iv)

            return iv.encodedAsString
        }


        private val ByteArray.encodedAsString get() = this.joinToString(separator = ",") { it.toString() }
        private val String.decodedFromString get() = this.split(",").map { it.toByte() }.toByteArray()
    }

    private fun initCipher(operationMode: Int): Cipher {
        val cipher = Cipher.getInstance("$ENCRYPTION_ALGORITHM/CBC/PKCS5PADDING")

        val key = SecretKeySpec(encryptionKey.decodedFromString, ENCRYPTION_ALGORITHM)
        val ivSpec = IvParameterSpec(iv.decodedFromString)
        cipher.init(operationMode, key, ivSpec)

        return cipher
    }

    fun encryptMessage(message: String): String {
        val cipher = initCipher(Cipher.ENCRYPT_MODE)
        val cipherText = cipher.doFinal(message.toByteArray())
        return cipherText.encodedAsString
    }

    fun decryptMessage(cipherText: String): String {
        return try {
            val cipher = initCipher(Cipher.DECRYPT_MODE)
            val cipherTextBytes: ByteArray = cipher.doFinal(cipherText.decodedFromString)
            cipherTextBytes.toString(StandardCharsets.UTF_8)
        }catch (e:Exception){
            Logger.e("$TAG: $e")
            ""
        }
    }

}