package com.rhefew.rsae2e

import com.rhefew.rsae2e.core.EncryptionManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.security.PublicKey

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Init
        val encryptionManager = EncryptionManager()
        encryptionManager.createMasterKey()

        //To get public key.
        val myPublicKey = encryptionManager.getMyPublicKey()

        //To get public key string that can share with others.
        val myPublicKeyString = String(Base64.encode(myPublicKey?.encoded, Base64.DEFAULT))

        //To get PublicKey from string that shared by others
        val othersPublicKeyString = findViewById<EditText>(R.id.txtOtherPublicKey).text.toString()
        val othersPublicKey = encryptionManager.getOtherPublicKey(othersPublicKeyString)

        findViewById<Button>(R.id.btnEncrypt).setOnClickListener{
            encrypt(othersPublicKey, encryptionManager)
        }

        findViewById<Button>(R.id.btnDecrypt).setOnClickListener{
            decrypt(myPublicKey, encryptionManager)
        }

    }

    private fun encrypt(othersPublicKey: PublicKey?, encryptionManager: EncryptionManager) {
        val message: String = findViewById<TextView>(R.id.txtInput).text.toString()
        val encryptedText = othersPublicKey?.let { encryptionManager.encryptOthers(message, it) }
        findViewById<EditText>(R.id.encryptedResult).setText(encryptedText)
    }

    private fun decrypt(publicKey: PublicKey?, encryptionManager: EncryptionManager) {
        val message: String = findViewById<TextView>(R.id.encryptedResult).text.toString()
        val decryptedText = publicKey?.let { encryptionManager.decrypt(message) }
        findViewById<EditText>(R.id.decryptedResult).setText(decryptedText)
    }
}