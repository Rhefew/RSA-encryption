package com.rhefew.rsae2e.core

import java.nio.charset.StandardCharsets
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.util.Base64

const val ALGORITHM_RSA = "RSA"
const val KEY_SIZE = 2048

const val TYPE_KEY_PUBLIC = "PUBLIC KEY"
const val TYPE_KEY_PRIVATE = "PRIVATE KEY"

data class KeyData (
    val publicKeyString: String,
    val publicKeyByteArray: ByteArray,
    val privateKeyString: String,
    val privateKeyByteArray: ByteArray,
)

fun generateRSAKeys(): KeyData {
    val keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM_RSA)
    keyPairGenerator.initialize(KEY_SIZE)
    val keyPair = keyPairGenerator.generateKeyPair()

    val publicKey = keyPair.public
    val privateKey = keyPair.private

    // Get encoded key bytes
    val publicKeyBytes = publicKey.encoded
    val privateKeyBytes = privateKey.encoded

//     Base64 encode the key bytes
    val publicKeyBase64 = formatKey(publicKeyBytes.toBase64(), TYPE_KEY_PUBLIC)
    val privateKeyBase64 = formatKey(privateKeyBytes.toBase64(), TYPE_KEY_PRIVATE)
//
    return KeyData(publicKeyBase64, publicKeyBytes, privateKeyBase64, privateKeyBytes)
//    return Pair(publicKeyBytes.toBase64(), privateKeyBytes.toBase64())
}

private fun formatKey(keyContent: String, keyType: String): String {
    val formattedKey = StringBuilder()
    formattedKey.append("-----BEGIN $keyType-----\n")
    formattedKey.append("$keyContent")
    formattedKey.append("-----END $keyType-----")
    return formattedKey.toString()
}

fun String.toBase64(): String {
    return String(
        android.util.Base64.encode(this.toByteArray(), android.util.Base64.DEFAULT),
        StandardCharsets.UTF_8
    )
}

fun ByteArray.toBase64(): String {
    return String(
        android.util.Base64.encode(this, android.util.Base64.DEFAULT),
        StandardCharsets.UTF_8
    )
}


fun String.fromBase64(): String {
    return String(
        android.util.Base64.decode(this, android.util.Base64.DEFAULT),
        StandardCharsets.UTF_8
    )
}


fun String.fromBase64ToByteArray(): ByteArray = android.util.Base64.decode(this, android.util.Base64.DEFAULT)