package com.rhefew.rsae2e

import android.os.Build
import android.os.Bundle
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import android.util.Log
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.rhefew.rsae2e.core.EncryptionManager.Companion.aesDecrypt
import java.security.*
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec

class MainActivity : AppCompatActivity() {
    private lateinit var privateKey: PrivateKey
    private lateinit var aesKey: String

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val generator = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA)
        val builder = KeyGenParameterSpec.Builder(KeyProperties.KEY_ALGORITHM_RSA, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
            .setKeySize(2048)
            .setBlockModes(KeyProperties.BLOCK_MODE_ECB)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
        generator.initialize(builder.build())

        val keys = generator.generateKeyPair()

        Log.i( "Cryptic", keys.private.toString())
        Log.i( "Cryptic", keys.public.toString())

        Log.i( "Cryptic", keys.private.encoded.contentToString())
        Log.i( "Cryptic", keys.public.encoded.contentToString())

        Log.i( "Cryptic", keys.private.encoded.toString(Charsets.UTF_8))
        Log.i( "Cryptic", keys.public.encoded.toString(Charsets.UTF_8))
    }
}