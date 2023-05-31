package com.rhefew.rsae2e.core

import java.security.KeyFactory
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher

class EncryptionManager {

    private val encryptionAlgorithm = "RSA"
    private val transformation = "RSA/ECB/PKCS1Padding"

    fun decryptMessage(encryptedMessage: String, privateKeyString: String): String {
        // Convert the private key string to bytes
        val privateKeyBytes = privateKeyString.fromBase64ToByteArray()

        // Create a private key object from the bytes
        val keySpec = PKCS8EncodedKeySpec(privateKeyBytes)
        val keyFactory = KeyFactory.getInstance(encryptionAlgorithm)
        val privateKey: PrivateKey = keyFactory.generatePrivate(keySpec)

        // Initialize the cipher with the private key and decryption mode
        val cipher = Cipher.getInstance(transformation)
        cipher.init(Cipher.DECRYPT_MODE, privateKey)

        // Decrypt the message
        val encryptedMessageBytes = encryptedMessage.fromBase64ToByteArray()
        val decryptedMessageBytes = cipher.doFinal(encryptedMessageBytes)

        // Convert the decrypted bytes to a string
        return String(decryptedMessageBytes)
    }

    fun encryptMessage(message: String, publicKeyBytes: ByteArray): String {
        // Convert the public key string to bytes

        // Create a public key object from the bytes
        val keySpec = X509EncodedKeySpec(publicKeyBytes)
        val keyFactory = KeyFactory.getInstance(encryptionAlgorithm)
        val publicKey: PublicKey = keyFactory.generatePublic(keySpec)

        // Initialize the cipher with the public key and encryption mode
        val cipher = Cipher.getInstance(transformation)
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)

        // Encrypt the message
        val encryptedMessageBytes = cipher.doFinal(message.toByteArray())

        // Base64 encode the encrypted message bytes
        return encryptedMessageBytes.toBase64()
    }
}