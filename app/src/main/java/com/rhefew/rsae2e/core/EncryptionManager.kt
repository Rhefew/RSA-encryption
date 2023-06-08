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
    private val bookendRegex = "-----.*?-----"

    // Maximum size of each chunk for encryption/decryption
    private val MAX_CHUNK_SIZE = 245

    fun encryptMessage(message: String, publicKeyString: ByteArray): Array<String> {
        val keySpec = X509EncodedKeySpec(publicKeyString)
        val keyFactory = KeyFactory.getInstance(encryptionAlgorithm)
        val publicKey: PublicKey = keyFactory.generatePublic(keySpec)

        val cipher = Cipher.getInstance(transformation)
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)

        val messageBytes = message.encodeToByteArray()

        // List to hold the encrypted chunks
        val encryptedChunks = mutableListOf<String>()

        // Encrypt the message in chunks
        var offset = 0
        while (offset < messageBytes.size) {
            val chunkSize = minOf(MAX_CHUNK_SIZE, messageBytes.size - offset)
            val chunk = cipher.doFinal(messageBytes, offset, chunkSize)
            val encryptedChunk = chunk.toBase64()
            encryptedChunks.add(encryptedChunk)
            offset += chunkSize
        }

        return encryptedChunks.toTypedArray()
    }

    fun decryptMessage(encryptedChunks: Array<String>, privateKeyString: String): String {
        val privateKeyBytes = privateKeyString.fromBase64ToByteArray()
        val keySpec = PKCS8EncodedKeySpec(privateKeyBytes)
        val keyFactory = KeyFactory.getInstance(encryptionAlgorithm)
        val privateKey: PrivateKey = keyFactory.generatePrivate(keySpec)

        val cipher = Cipher.getInstance(transformation)
        cipher.init(Cipher.DECRYPT_MODE, privateKey)

        // List to hold the decrypted chunks
        val decryptedChunks = mutableListOf<ByteArray>()

        // Decrypt the chunks
        for (encryptedChunk in encryptedChunks) {
            val encryptedChunkBytes = encryptedChunk.fromBase64ToByteArray()
            val decryptedChunk = cipher.doFinal(encryptedChunkBytes)
            decryptedChunks.add(decryptedChunk)
        }

        // Concatenate the decrypted chunks and convert to string
        val decryptedMessageBytes = concatByteArrays(*decryptedChunks.toTypedArray())
        val decryptedMessage = String(decryptedMessageBytes)

        return decryptedMessage
    }

    // Concatenate multiple byte arrays into a single byte array
    private fun concatByteArrays(vararg byteArrays: ByteArray): ByteArray {
        val totalSize = byteArrays.sumOf { it.size }
        val result = ByteArray(totalSize)
        var offset = 0
        for (byteArray in byteArrays) {
            System.arraycopy(byteArray, 0, result, offset, byteArray.size)
            offset += byteArray.size
        }
        return result
    }
}