package com.rhefew.rsae2e

import android.os.Build
import com.rhefew.rsae2e.core.EncryptionManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.Base64
import android.util.Log
import android.widget.EditText
import androidx.annotation.RequiresApi
import com.rhefew.rsae2e.core.EncryptionManager.Companion.aesDecrypt
import com.rhefew.rsae2e.core.EncryptionManager.Companion.generateRSAKeyPair
import java.nio.charset.StandardCharsets
import java.security.KeyFactory
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.NoSuchAlgorithmException
import java.security.PrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.RSAPublicKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.spec.SecretKeySpec

class MainActivity : AppCompatActivity() {
    private lateinit var privateKey: PrivateKey
    private lateinit var aesKey: String

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Generate the RSA key pair
        val keyPair = generateRSAKeyPair()

        // Retrieve the public key from the generated key pair
        val publicKey = keyPair.public as RSAPublicKey

        // Convert the public key to PEM format
        val pemPublicKey = convertRSAPublicKeyToPEM(publicKey)

        // Print the public key details
        Log.d("KEEEEEEY", "Generated RSA public key: $pemPublicKey")

    }

    fun generateRSAKeyPair(): KeyPair {
        try {
            // Create a KeyPairGenerator instance for RSA
            val keyPairGenerator = KeyPairGenerator.getInstance("RSA")

            // Set the key size to 2048 bits
            keyPairGenerator.initialize(2048)

            // Generate the key pair
            return keyPairGenerator.generateKeyPair()
        } catch (e: NoSuchAlgorithmException) {
            // Handle any exceptions that may occur
            e.printStackTrace()
            throw RuntimeException("Failed to generate RSA key pair")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertRSAPublicKeyToPEM(publicKey: RSAPublicKey): String {
        val encodedKey = publicKey.encoded
        val encodedKeyBase64 = Base64.getEncoder().encodeToString(encodedKey)
        val pemKey = StringBuilder()
        pemKey.append("-----BEGIN PUBLIC KEY-----\n")
        pemKey.append(chunkSplit(encodedKeyBase64, 64))
        pemKey.append("-----END PUBLIC KEY-----")
        return pemKey.toString()
    }

    fun chunkSplit(str: String, chunkLen: Int): String {
        val sb = StringBuilder()
        var i = 0
        while (i < str.length) {
            sb.append(str.substring(i, Math.min(i + chunkLen, str.length)))
            sb.append('\n')
            i += chunkLen
        }
        return sb.toString()
    }


    private fun receiveEncryptedAesKey(): ByteArray {
        // TODO: Receive the encrypted AES key from the sender
        // In this example, we'll just use a hardcoded value
        return findViewById<EditText>(R.id.txtOtherPublicKey).text.toString().decodeBase64()
    }

    private fun receiveMessage() {
        // TODO: Receive the encrypted message from the sender
        // In this example, we'll just use a hardcoded value
        val encryptedMessage = findViewById<EditText>(R.id.txtOtherPublicKey).text.toString().decodeBase64()

        // Decrypt the message with the AES key
        val decryptedMessage = aesDecrypt(encryptedMessage, aesKey.decodeBase64())

        // Display the decrypted message
        findViewById<EditText>(R.id.encryptedResult).setText(String(decryptedMessage))
    }

    private fun generatePrivateKey(privateKeyBytes: ByteArray): PrivateKey {
        val keySpec = PKCS8EncodedKeySpec(privateKeyBytes)
        val keyFactory = java.security.KeyFactory.getInstance("RSA")
        return keyFactory.generatePrivate(keySpec)
    }

    private fun String.encodeBase64(): String {
        return android.util.Base64.encodeToString(this.toByteArray(), android.util.Base64.DEFAULT)
    }

    private fun String.decodeBase64(): ByteArray {
        return android.util.Base64.decode(this, android.util.Base64.DEFAULT)
    }
}