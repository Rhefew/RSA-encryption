package com.rhefew.rsae2e

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.security.*
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*
import javax.crypto.Cipher

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val keys = generateRSAKeys()

        // Format the keys
        val formattedPublicKey = formatKey(keys.first, "PUBLIC KEY")
        val formattedPrivateKey = formatKey(keys.second, "PRIVATE KEY")

        //Rhefew data
        val myLastPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAufY9xOjJ31ch/BF7jaP1vIKEzZGNsdfYTigABVuBPLlJ7Btvxl6ebx4T3VirmDIeuo3m13kjpPJCZXSSIj6ZsA8FHNWArj3h156XokOQ6NiuJmPf7Bd8N3Y49w3QngD2wVj/3bZmsoN6tFc8/BK9FUS+3d8u1D0GXH8+KfOFoKHKEQ7T3HjRTdFe4YYkuCyA2NRgXxjkdaEQyQTwrlq7ofGFDHri9hegj601+FjSV88xgjeWPvicUC3tXIYDgFYjwGhe/hgj253TaWsEF9fFjLARYXzep9wTxppAliaHT6Dx2CvtQDbNpvH3O1okcjUlbhgTcmOxi5M89mFHlu6rowIDAQAB"
        val myLastPrivateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC59j3E6MnfVyH8EXuNo/W8goTNkY2x19hOKAAFW4E8uUnsG2/GXp5vHhPdWKuYMh66jebXeSOk8kJldJIiPpmwDwUc1YCuPeHXnpeiQ5Do2K4mY9/sF3w3djj3DdCeAPbBWP/dtmayg3q0Vzz8Er0VRL7d3y7UPQZcfz4p84WgocoRDtPceNFN0V7hhiS4LIDY1GBfGOR1oRDJBPCuWruh8YUMeuL2F6CPrTX4WNJXzzGCN5Y++JxQLe1chgOAViPAaF7+GCPbndNpawQX18WMsBFhfN6n3BPGmkCWJodPoPHYK+1ANs2m8fc7WiRyNSVuGBNyY7GLkzz2YUeW7qujAgMBAAECggEAGvu5g8h5fmBQzJgm0usfHZ7csU10/4C/4LtaIWzhKFQEf2+NvP1+xMXbAQu8PmXLYqtzewGXY/hJHRqYl8J4n3zWc5QhS2csTZK0EvX14WuLfYORetr/ibasVmDZTAv8whptahjs9uswE1nzPepkKGFkxqH7VA2UzjgiZ4jv81o3O24Wllz5cDnPYLYeCw/ZoKoB5c+9YwrzEbbtDuJFLpsOXbhmbFjMzSHv1I2adXDAdJIRLtakbFYErtmdO2dOXUL/IPaB6RezrjTGozo5zM5xvPQWUZEzDla08r9nLCtdMEUn2gpxbPWvtRwjibj9mo9X4328pvlg0tvzQHoWCQKBgQDaszocX38lHuvuMD9SYaM4iPTRj4Oc/a/++bKBGChnQKTcteSmU717cSI/8MwCn6C9UQUluGgRB0/qfJKwPMnyM9a8v+vQ3XoNEjISeKADhEAHcLzuaNk28PR8V0fp8A8bO4DfoTBcQrr1JPO4j6NPoWi1HOZ+tAc5z1McnK6uewKBgQDZrZ1QYllrpkSulP8xY6mwiL00qL8mCgenHqSrpr5r/h476J+FQkfO5RoEbqTIo2E3FmkbsDqNmbKMMxYXPoakBscrA2byZkPX12s1YXBl19AQGDFjKlX2djiOWiFrg8NjpQSIkzZt9jOSQIH/WHQbL4Zeuo75x0XPKaw2/94C+QKBgG3aLutHkJ79vDaFZ5ki0BC6wnZs5wg5aWji1aYAk3Lhxq3Sc8qUL45M0o5ZjrAWrG/LvZEn4qYDDUJUVn5N2oZdQZTHtlTQmtjr9djPXFjH2j2ihFQguFLfj37RfJ7mZUAhkGdEGP2Oy0gNypo/FCIQin7GyISGO0bve+jYTun3AoGBALYIRrbTF/4609K/DM0rj9rt7nOb7gWMe6vYp1JYJYO/xV7KAKR6sfwlgGVWdXzHi+N6XTlnXP/PGQ2/JukXQ5bliCbDOJLazD8yiRfhvRGdsQPUh3BrFen+IPuCCdKUJJAujiT8HpdEPQo/4bY8LRQS4SZUkn30k79TFQ4FWI5ZAoGAYRgZaP2XPwCoAHHEIwpIkDZJk/hElw9BdtipDwpzMCHGpysQi1vW5fpStsvE2XlOyMFzgUdFAU8/qmCuOqrthVMBjBXbK4icOAlvUlGErVkbWPM0hiO+54ZpukFKwx/PIM4CtSx2pTOyQs+Xr6o3xvYQb4gPHpNBJKrB203B0i0="

        val encrypted = encryptMessage("Hello Krzys!", myLastPublicKey)
        setClipboard(encrypted)

        println("========================================================TEST==========================================" + decryptMessage(encrypted, myLastPrivateKey))

//        val decryptedMessage = decryptMessage(encryptedMessage, privateKey)
//        println(decryptedMessage)
//



        val kmessage = "Oxr4VikU3YfDcp0kBRZgpRec0KMjjL2RVYBse/BN5RuTJF+gEOKyHckbz+zy2EFdkfK9VdKF4b8RrZVACmB29gMPdr/Z6XaSj3LQVk/ef4PBZq62qlueaQQRWhzWtTrhIVMqbri32uDhdYgRL6LqCcn3ZMwn+KggzqciIEy7Jgby7wTLGmzN0nNkY54VZ8yJziHBsoI44BqSIpOwL3HxvVlOpgtnc2oohO3Hk2KqrOkRokSH2QqGQey6D5h4Db2jeXkzuqZ+00ZuVyVVi0exiEw2gPg+9IiQlx/ZUafKCqB/G7eflx46W5BC1praEaqEzarpW3ZNbHLWRbimj8z9jQ=="
        val kLastPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAh2VnqIEmHHqX8KVA7yEh\n" +
                "zQWXsX6VvSMfGisVwzASWaP8bYEVkYSG/owlzJjhfFy30yswAwZQEp7Qt/rQRMpJ\n" +
                "Erp3rREkqmKLYwULdZ19ZUyNuNzRMURkFASI8/aonXtSQyjtrurKTCS5OZehX6w1\n" +
                "T/0r+B6n6v+mVnRhMMUJ+HY7pt3PO9RJuXbp+Gb2b8psBaAnqMMPkkBxUWjSswEx\n" +
                "85s71oKYER1AJNVlKdnDxA/TDFaklgFre/z97z7zVdiIY4htwxoN5xnv1uFfvFuf\n" +
                "PiricURGYtKvrhe1epuAFI9bLuguZAJCH+cvQLb+KcHCqvfGAcaLQB6ipHx327hG\n" +
                "6wIDAQAB".trimIndent()

        val kLastPrivateKey = "MIIEpAIBAAKCAQEAh2VnqIEmHHqX8KVA7yEhzQWXsX6VvSMfGisVwzASWaP8bYEVkYSG/owlzJjhfFy30yswAwZQEp7Qt/rQRMpJErp3rREkqmKLYwULdZ19ZUyNuNzRMURkFASI8/aonXtSQyjtrurKTCS5OZehX6w1T/0r+B6n6v+mVnRhMMUJ+HY7pt3PO9RJuXbp+Gb2b8psBaAnqMMPkkBxUWjSswEx85s71oKYER1AJNVlKdnDxA/TDFaklgFre/z97z7zVdiIY4htwxoN5xnv1uFfvFufPiricURGYtKvrhe1epuAFI9bLuguZAJCH+cvQLb+KcHCqvfGAcaLQB6ipHx327hG6wIDAQABAoIBAGAmvldm5/a/hNlLsfJf+A4OE6zj9Vis8XB2+mxz3gQ0tZXaXsPJdsicJFfIlhDoE09uUxlCyWh/yj9taawAb6ZSL50DGKO7ebsnIP00i5F9rQOEJTc0l1Gz6sI9/35ezouNLEXUzTcHuVZR/TLhK7cTVr9Mnfj/9lNdMIwe0bbUxDMoVUIVKQtENlXpyecDLE5F7uucDfYZocRgdIzRBVdTmoXl8kNbpqHQrWE3T0AMAXHgOZwgTRvk9Pw3QpXIj7P7LJ6lJEYFFcZ9cHo1yK5BVVeYzjunceAgU/jZ7+NhW2JmAjvhwBMkUgnQuH5Zx3PKY9OCSfPcdLoM3kGgO7kCgYEA+XCJE4hbz5DOWnWETrz90vFnM66TgWjDsUbvqFDBS7Ezoa/l0WQ2fM5gbDFc2TxGXEY83O8+iua0yQWutiLJvqliFe2PdWDBs78mkRKtGvjFhZet8Elpji6op6f55ysn/vHLfG5Z9PVaBw2ELSwIRAKjLc0Mh8sdcodlYcZ3dXUCgYEAivUFMuUqYrD0Xvwkz5hH379PPXeCiHqqk2YKEmOVRbJ2M41gTAGo1SmQv8baWHNzOsCzdNg0udNKBFu26hNDkBhbo9BceAVy6G0wsdpdjK5hAMQZ9C/279/YjbWDD1B3PHJwAhYcUZsRZ7r2vOiGKNAtWEvATbac2BdnpTFoXt8CgYEAlzFrJPfGDq5LJfGNXNyZTb8dOupaZlpGI7JHmdI5F9Q045Bm0ODZVhURxQK2sMYkL9IzGge59R+Z/S8Ak03K01KALaJtDkyznGwEyHJ1kp+vuiYSPE+DN7d4awcaJtC10YZmPOn2hNAi3ZuHvomuCOtAVYawTa8EDmObRAS0a/ECgYA/mIf6lRti863D0w5d3En4wyvW691X7RwoPakaZW0p6tViHrAV2SSV+mH/A1lm5d+04gC/1zu6WFMlz3vVgV+IkT1PqcrIO2Ytrdt9an0qJ5zJpvZh5ZnUitQndw02cxM/HPjaW5g2WQwMN2s5ZgoQn8ZBBNpMNultlLigyA4GbQKBgQC0URVUeaQH5CWGW0NsQnCW1XGBp1EbP45dPzYLwgPlGDCVN1SHHSkCDUS5pqnBnYhdMlPCo6CotDUTnSczBAkmH8jrUj66CHc+/CFvJAnoqr9cfyO9iR+e35vl8oTZUJttZ7L4hdiNb0A6V7FbYnviwJWq7WtUsZVxP6fTy1nb/A=="

        val kdecrypted = decryptMessage(kmessage, kLastPrivateKey)
        println("YELPPPP" + kdecrypted)
    }

    fun generateRSAKeys(): Pair<String, String> {
        val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
        keyPairGenerator.initialize(2048)
        val keyPair = keyPairGenerator.generateKeyPair()

        val publicKey = keyPair.public
        val privateKey = keyPair.private

        // Get encoded key bytes
        val publicKeyBytes = publicKey.encoded
        val privateKeyBytes = privateKey.encoded

        // Base64 encode the key bytes
        val publicKeyBase64 = Base64.getEncoder().encodeToString(publicKeyBytes)
        val privateKeyBase64 = Base64.getEncoder().encodeToString(privateKeyBytes)

        return Pair(publicKeyBase64, privateKeyBase64)
    }

    private fun formatKey(keyContent: String, keyType: String): String {
        val formattedKey = StringBuilder()
        formattedKey.append("-----BEGIN $keyType-----\n")
        keyContent.chunked(64).forEach { formattedKey.append(it).append("\n") }
        formattedKey.append("-----END $keyType-----")
        return formattedKey.toString()
    }

    private fun setClipboard(text: String) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("", text)
        clipboard.setPrimaryClip(clip)
    }

    private fun decryptMessage(encryptedMessage: String, privateKeyString: String): String {
        // Convert the private key string to bytes
        val privateKeyBytes = Base64.getDecoder().decode(privateKeyString)

        // Create a private key object from the bytes
        val keySpec = PKCS8EncodedKeySpec(privateKeyBytes)
        val keyFactory = KeyFactory.getInstance("RSA")
        val privateKey: PrivateKey = keyFactory.generatePrivate(keySpec)

        // Initialize the cipher with the private key and decryption mode
        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        cipher.init(Cipher.DECRYPT_MODE, privateKey)

        // Decrypt the message
        val encryptedMessageBytes = Base64.getDecoder().decode(encryptedMessage)
        val decryptedMessageBytes = cipher.doFinal(encryptedMessageBytes)

        // Convert the decrypted bytes to a string
        return String(decryptedMessageBytes)
    }

    fun encryptMessage(message: String, publicKeyString: String): String {
        // Convert the public key string to bytes
        val publicKeyBytes = Base64.getDecoder().decode(publicKeyString)

        // Create a public key object from the bytes
        val keySpec = X509EncodedKeySpec(publicKeyBytes)
        val keyFactory = KeyFactory.getInstance("RSA")
        val publicKey: PublicKey = keyFactory.generatePublic(keySpec)

        // Initialize the cipher with the public key and encryption mode
        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)

        // Encrypt the message
        val encryptedMessageBytes = cipher.doFinal(message.toByteArray())

        // Base64 encode the encrypted message bytes

        return Base64.getEncoder().encodeToString(encryptedMessageBytes)
    }
}