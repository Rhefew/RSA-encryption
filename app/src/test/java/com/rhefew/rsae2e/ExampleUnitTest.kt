package com.rhefew.rsae2e

import com.rhefew.rsae2e.core.EncryptionManager
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    private val publicKeyString = "<your generated public key string>"
    private val privateKeyString = "<your generated private key string>"
    private val remotePublicKeyString = "<third party public key string>"
    private val remoteEncryptedMessage = "<encrypted message provided by third party>"

    private val encryptionManager = EncryptionManager()
    @Test
    fun testEncryptionDecryptionWithOwnKeys() {
        val message = "Hello, World!"

        val encryptedMessage = encryptionManager.encryptMessage(message, publicKeyString)
        val decryptedMessage = encryptionManager.decryptMessage(encryptedMessage, privateKeyString)

        assertEquals(message, decryptedMessage)
    }

    @Test
    fun testEncryptionDecryptionWithRemoteKeys() {
        val message = "Hello, World!"

        val encryptedMessage = encryptionManager.encryptMessage(message, remotePublicKeyString)
        val decryptedMessage = encryptionManager.decryptMessage(encryptedMessage, privateKeyString)

        assertEquals(message, decryptedMessage)
    }

    @Test
    fun testRemoteEncryptionDecryption() {
        val message = "Hello, World!"

        val encryptedMessage = remoteEncryptedMessage
        val decryptedMessage = encryptionManager.decryptMessage(encryptedMessage, privateKeyString)

        assertEquals(message, decryptedMessage)
    }
}