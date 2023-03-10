package com.rhefew.rsae2e.core

import android.util.Base64
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec

class EncryptionManager() {

    companion object {
        const val MASTER_KEY = "master_key"
        const val KEY_PAIR_ALGORITHM = "RSA"
        const val KEY_SIZE: Int = 2048
        const val KEY_PROVIDER = "AndroidKeyStore"
        const val TRANSFORMATION_ASYMMETRIC = "RSA/None/PKCS1Padding"
    }

    private val keyStoreWrapper = KeyStoreWrapper()

    /*
     * Encryption Stage
     */
    fun createMasterKey() {
        if (keyStoreWrapper.getAndroidKeyStoreAsymmetricKeyPair(MASTER_KEY) == null) {
            keyStoreWrapper.createAndroidKeyStoreAsymmetricKey(MASTER_KEY)
        }
    }

    fun removeMasterKey() {
        keyStoreWrapper.removeAndroidKeyStoreKey(MASTER_KEY)
    }

    fun encrypt(data: String): String? {
        val masterKey = keyStoreWrapper.getAndroidKeyStoreAsymmetricKeyPair(MASTER_KEY)
        return CipherWrapper(TRANSFORMATION_ASYMMETRIC).encrypt(data, masterKey?.public)
    }

    fun encrypt(data: ByteArray): ByteArray? {
        val masterKey = keyStoreWrapper.getAndroidKeyStoreAsymmetricKeyPair(MASTER_KEY)
        return CipherWrapper(TRANSFORMATION_ASYMMETRIC).encrypt(data, masterKey?.public)
    }

    fun encryptOthers(data: String, key: PublicKey): String? {
        return CipherWrapper(TRANSFORMATION_ASYMMETRIC).encrypt(data, key)
    }

    fun encryptOthers(data: ByteArray, key: PublicKey): ByteArray? {
        return CipherWrapper(TRANSFORMATION_ASYMMETRIC).encrypt(data, key)
    }

    fun decrypt(data: String): String? {
        val masterKey = keyStoreWrapper.getAndroidKeyStoreAsymmetricKeyPair(MASTER_KEY)
        return CipherWrapper(TRANSFORMATION_ASYMMETRIC).decrypt(data, masterKey?.private)
    }

    fun decrypt(data: ByteArray): ByteArray? {
        val masterKey = keyStoreWrapper.getAndroidKeyStoreAsymmetricKeyPair(MASTER_KEY)
        return CipherWrapper(TRANSFORMATION_ASYMMETRIC).decrypt(data, masterKey?.private)
    }

    /*
     * Manage Keys
     */
    fun getMyPublicKey(): PublicKey? {
        val masterKey = keyStoreWrapper.getAndroidKeyStoreAsymmetricKeyPair(MASTER_KEY)
        return masterKey?.public
    }

    fun getOtherPublicKey(key: String): PublicKey? {
        return try {
            val publicBytes: ByteArray =
                Base64.decode(key, Base64.DEFAULT)
            val keySpec = X509EncodedKeySpec(publicBytes)
            val keyFactory = KeyFactory.getInstance(KEY_PAIR_ALGORITHM)
            keyFactory.generatePublic(keySpec)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}