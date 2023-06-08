package com.rhefew.rsae2e

import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.rhefew.rsae2e.core.EncryptionManager
import com.rhefew.rsae2e.core.generateRSAKeys
import com.rhefew.rsae2e.core.toBase64
import java.security.*
import java.util.*

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val publicKeyEdit = findViewById<EditText>(R.id.txtPublicKey)
        val privateKeyEdit = findViewById<EditText>(R.id.txtPrivateKey)

        val keys = generateRSAKeys()

        publicKeyEdit.setText(keys.publicKeyString)
        privateKeyEdit.setText(keys.privateKeyString)

        val input = findViewById<TextView>(R.id.txtInput).text
        val encryptedResult = findViewById<EditText>(R.id.encryptedResult)
        val decryptedResult = findViewById<EditText>(R.id.decryptedResult)


        var encryptedChunks : Array<String> = arrayOf()
        val buttonEncrypt = findViewById<Button>(R.id.btnEncrypt)
        buttonEncrypt.setOnClickListener{
            encryptedChunks = EncryptionManager().encryptMessage(input.toString(), keys.publicKeyByteArray)
            encryptedResult.setText(encryptedChunks.joinToString())
        }

        val buttonDecrypt = findViewById<Button>(R.id.btnDecrypt)
        buttonDecrypt.setOnClickListener{
            val decrypted = EncryptionManager().decryptMessage(encryptedChunks, keys.privateKeyByteArray.toBase64())
            decryptedResult.setText(decrypted)
        }
    }
}