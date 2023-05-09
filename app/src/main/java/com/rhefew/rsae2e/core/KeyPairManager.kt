package com.rhefew.rsae2e.core

import java.security.KeyPair
import java.security.KeyPairGenerator
import java.util.Base64

fun generateRSAKeyPair(): KeyPair {
    val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
    keyPairGenerator.initialize(2048) // Set the key size to 2048 bits
    return keyPairGenerator.generateKeyPair()
}

fun String.encodeBase64(): String {
    return android.util.Base64.encodeToString(this.toByteArray(), android.util.Base64.NO_PADDING)
}

fun String.decodeBase64(): ByteArray {
    return android.util.Base64.decode(this, android.util.Base64.NO_PADDING)
}