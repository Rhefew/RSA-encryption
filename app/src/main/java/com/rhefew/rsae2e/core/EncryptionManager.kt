package com.rhefew.rsae2e.core

import android.util.Base64
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec

import java.security.*
import javax.crypto.*
import javax.crypto.spec.*

class EncryptionManager {

    companion object {
        private const val RSA_ALGORITHM = "RSA"
        private const val AES_ALGORITHM = "AES"
        private const val AES_KEY_SIZE = 2048

        fun generateRSAKeyPair(): KeyPair {
            val keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM)
            keyPairGenerator.initialize(2048)
            return keyPairGenerator.generateKeyPair()
        }

        fun rsaEncrypt(data: ByteArray, publicKey: PublicKey): ByteArray {
            val cipher = Cipher.getInstance(RSA_ALGORITHM)
            cipher.init(Cipher.ENCRYPT_MODE, publicKey)
            return cipher.doFinal(data)
        }

        fun rsaDecrypt(data: ByteArray, privateKey: PrivateKey): ByteArray {
            val cipher = Cipher.getInstance(RSA_ALGORITHM)
            cipher.init(Cipher.DECRYPT_MODE, privateKey)
            return cipher.doFinal(data)
        }

        fun aesEncrypt(data: ByteArray, key: ByteArray): ByteArray {
            val secretKey = SecretKeySpec(key, AES_ALGORITHM)
            val cipher = Cipher.getInstance(AES_ALGORITHM)
            cipher.init(Cipher.ENCRYPT_MODE, secretKey)
            return cipher.doFinal(data)
        }

        fun aesDecrypt(data: ByteArray, key: ByteArray): ByteArray {
            val secretKey = SecretKeySpec(key, AES_ALGORITHM)
            val cipher = Cipher.getInstance(AES_ALGORITHM)
            cipher.init(Cipher.DECRYPT_MODE, secretKey)
            return cipher.doFinal(data)
        }

        fun generateAESKey(): ByteArray {
            val keyGenerator = KeyGenerator.getInstance(AES_ALGORITHM)
            keyGenerator.init(AES_KEY_SIZE)
            return keyGenerator.generateKey().encoded
        }

        fun exchangeAESKey(publicKey: PublicKey): ByteArray {
            // Generate a random AES key
            val aesKey = generateAESKey()

            // Encrypt the AES key with the recipient's RSA public key
            val encryptedAesKey = rsaEncrypt(aesKey, publicKey)

            // Return the encrypted AES key
            return encryptedAesKey
        }
    }
}