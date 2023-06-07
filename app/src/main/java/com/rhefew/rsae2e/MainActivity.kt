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


//        //Rhefew data
//        val myLastPublicKey =
//            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAufY9xOjJ31ch/BF7jaP1vIKEzZGNsdfYTigABVuBPLlJ7Btvxl6ebx4T3VirmDIeuo3m13kjpPJCZXSSIj6ZsA8FHNWArj3h156XokOQ6NiuJmPf7Bd8N3Y49w3QngD2wVj/3bZmsoN6tFc8/BK9FUS+3d8u1D0GXH8+KfOFoKHKEQ7T3HjRTdFe4YYkuCyA2NRgXxjkdaEQyQTwrlq7ofGFDHri9hegj601+FjSV88xgjeWPvicUC3tXIYDgFYjwGhe/hgj253TaWsEF9fFjLARYXzep9wTxppAliaHT6Dx2CvtQDbNpvH3O1okcjUlbhgTcmOxi5M89mFHlu6rowIDAQAB"
//        val myLastPrivateKey =
//            "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC59j3E6MnfVyH8EXuNo/W8goTNkY2x19hOKAAFW4E8uUnsG2/GXp5vHhPdWKuYMh66jebXeSOk8kJldJIiPpmwDwUc1YCuPeHXnpeiQ5Do2K4mY9/sF3w3djj3DdCeAPbBWP/dtmayg3q0Vzz8Er0VRL7d3y7UPQZcfz4p84WgocoRDtPceNFN0V7hhiS4LIDY1GBfGOR1oRDJBPCuWruh8YUMeuL2F6CPrTX4WNJXzzGCN5Y++JxQLe1chgOAViPAaF7+GCPbndNpawQX18WMsBFhfN6n3BPGmkCWJodPoPHYK+1ANs2m8fc7WiRyNSVuGBNyY7GLkzz2YUeW7qujAgMBAAECggEAGvu5g8h5fmBQzJgm0usfHZ7csU10/4C/4LtaIWzhKFQEf2+NvP1+xMXbAQu8PmXLYqtzewGXY/hJHRqYl8J4n3zWc5QhS2csTZK0EvX14WuLfYORetr/ibasVmDZTAv8whptahjs9uswE1nzPepkKGFkxqH7VA2UzjgiZ4jv81o3O24Wllz5cDnPYLYeCw/ZoKoB5c+9YwrzEbbtDuJFLpsOXbhmbFjMzSHv1I2adXDAdJIRLtakbFYErtmdO2dOXUL/IPaB6RezrjTGozo5zM5xvPQWUZEzDla08r9nLCtdMEUn2gpxbPWvtRwjibj9mo9X4328pvlg0tvzQHoWCQKBgQDaszocX38lHuvuMD9SYaM4iPTRj4Oc/a/++bKBGChnQKTcteSmU717cSI/8MwCn6C9UQUluGgRB0/qfJKwPMnyM9a8v+vQ3XoNEjISeKADhEAHcLzuaNk28PR8V0fp8A8bO4DfoTBcQrr1JPO4j6NPoWi1HOZ+tAc5z1McnK6uewKBgQDZrZ1QYllrpkSulP8xY6mwiL00qL8mCgenHqSrpr5r/h476J+FQkfO5RoEbqTIo2E3FmkbsDqNmbKMMxYXPoakBscrA2byZkPX12s1YXBl19AQGDFjKlX2djiOWiFrg8NjpQSIkzZt9jOSQIH/WHQbL4Zeuo75x0XPKaw2/94C+QKBgG3aLutHkJ79vDaFZ5ki0BC6wnZs5wg5aWji1aYAk3Lhxq3Sc8qUL45M0o5ZjrAWrG/LvZEn4qYDDUJUVn5N2oZdQZTHtlTQmtjr9djPXFjH2j2ihFQguFLfj37RfJ7mZUAhkGdEGP2Oy0gNypo/FCIQin7GyISGO0bve+jYTun3AoGBALYIRrbTF/4609K/DM0rj9rt7nOb7gWMe6vYp1JYJYO/xV7KAKR6sfwlgGVWdXzHi+N6XTlnXP/PGQ2/JukXQ5bliCbDOJLazD8yiRfhvRGdsQPUh3BrFen+IPuCCdKUJJAujiT8HpdEPQo/4bY8LRQS4SZUkn30k79TFQ4FWI5ZAoGAYRgZaP2XPwCoAHHEIwpIkDZJk/hElw9BdtipDwpzMCHGpysQi1vW5fpStsvE2XlOyMFzgUdFAU8/qmCuOqrthVMBjBXbK4icOAlvUlGErVkbWPM0hiO+54ZpukFKwx/PIM4CtSx2pTOyQs+Xr6o3xvYQb4gPHpNBJKrB203B0i0="
//
//        val encryptionManager = EncryptionManager()
//
//        with(encryptionManager) {
//            val encrypted = encryptMessage("Hello Krzys!", myLastPublicKey)
//            assert(decryptMessage(encrypted, myLastPrivateKey) == "Hello Krzys!")
//
//            val kmessage = "Oxr4VikU3YfDcp0kBRZgpRec0KMjjL2RVYBse/BN5RuTJF+gEOKyHckbz+zy2EFdkfK9VdKF4b8RrZVACmB29gMPdr/Z6XaSj3LQVk/ef4PBZq62qlueaQQRWhzWtTrhIVMqbri32uDhdYgRL6LqCcn3ZMwn+KggzqciIEy7Jgby7wTLGmzN0nNkY54VZ8yJziHBsoI44BqSIpOwL3HxvVlOpgtnc2oohO3Hk2KqrOkRokSH2QqGQey6D5h4Db2jeXkzuqZ+00ZuVyVVi0exiEw2gPg+9IiQlx/ZUafKCqB/G7eflx46W5BC1praEaqEzarpW3ZNbHLWRbimj8z9jQ=="
//            val kLastPrivateKey = "MIIEpAIBAAKCAQEAh2VnqIEmHHqX8KVA7yEhzQWXsX6VvSMfGisVwzASWaP8bYEVkYSG/owlzJjhfFy30yswAwZQEp7Qt/rQRMpJErp3rREkqmKLYwULdZ19ZUyNuNzRMURkFASI8/aonXtSQyjtrurKTCS5OZehX6w1T/0r+B6n6v+mVnRhMMUJ+HY7pt3PO9RJuXbp+Gb2b8psBaAnqMMPkkBxUWjSswEx85s71oKYER1AJNVlKdnDxA/TDFaklgFre/z97z7zVdiIY4htwxoN5xnv1uFfvFufPiricURGYtKvrhe1epuAFI9bLuguZAJCH+cvQLb+KcHCqvfGAcaLQB6ipHx327hG6wIDAQABAoIBAGAmvldm5/a/hNlLsfJf+A4OE6zj9Vis8XB2+mxz3gQ0tZXaXsPJdsicJFfIlhDoE09uUxlCyWh/yj9taawAb6ZSL50DGKO7ebsnIP00i5F9rQOEJTc0l1Gz6sI9/35ezouNLEXUzTcHuVZR/TLhK7cTVr9Mnfj/9lNdMIwe0bbUxDMoVUIVKQtENlXpyecDLE5F7uucDfYZocRgdIzRBVdTmoXl8kNbpqHQrWE3T0AMAXHgOZwgTRvk9Pw3QpXIj7P7LJ6lJEYFFcZ9cHo1yK5BVVeYzjunceAgU/jZ7+NhW2JmAjvhwBMkUgnQuH5Zx3PKY9OCSfPcdLoM3kGgO7kCgYEA+XCJE4hbz5DOWnWETrz90vFnM66TgWjDsUbvqFDBS7Ezoa/l0WQ2fM5gbDFc2TxGXEY83O8+iua0yQWutiLJvqliFe2PdWDBs78mkRKtGvjFhZet8Elpji6op6f55ysn/vHLfG5Z9PVaBw2ELSwIRAKjLc0Mh8sdcodlYcZ3dXUCgYEAivUFMuUqYrD0Xvwkz5hH379PPXeCiHqqk2YKEmOVRbJ2M41gTAGo1SmQv8baWHNzOsCzdNg0udNKBFu26hNDkBhbo9BceAVy6G0wsdpdjK5hAMQZ9C/279/YjbWDD1B3PHJwAhYcUZsRZ7r2vOiGKNAtWEvATbac2BdnpTFoXt8CgYEAlzFrJPfGDq5LJfGNXNyZTb8dOupaZlpGI7JHmdI5F9Q045Bm0ODZVhURxQK2sMYkL9IzGge59R+Z/S8Ak03K01KALaJtDkyznGwEyHJ1kp+vuiYSPE+DN7d4awcaJtC10YZmPOn2hNAi3ZuHvomuCOtAVYawTa8EDmObRAS0a/ECgYA/mIf6lRti863D0w5d3En4wyvW691X7RwoPakaZW0p6tViHrAV2SSV+mH/A1lm5d+04gC/1zu6WFMlz3vVgV+IkT1PqcrIO2Ytrdt9an0qJ5zJpvZh5ZnUitQndw02cxM/HPjaW5g2WQwMN2s5ZgoQn8ZBBNpMNultlLigyA4GbQKBgQC0URVUeaQH5CWGW0NsQnCW1XGBp1EbP45dPzYLwgPlGDCVN1SHHSkCDUS5pqnBnYhdMlPCo6CotDUTnSczBAkmH8jrUj66CHc+/CFvJAnoqr9cfyO9iR+e35vl8oTZUJttZ7L4hdiNb0A6V7FbYnviwJWq7WtUsZVxP6fTy1nb/A=="
//
//            val kdecrypted = decryptMessage(kmessage, kLastPrivateKey)
//            assert(kdecrypted == "Hello worlds")
//        }

        val input = findViewById<TextView>(R.id.txtInput).text
        val encryptedResult = findViewById<EditText>(R.id.encryptedResult)
        val decryptedResult = findViewById<EditText>(R.id.decryptedResult)


        val buttonEncrypt = findViewById<Button>(R.id.btnEncrypt)
        buttonEncrypt.setOnClickListener{
            val encrypted = EncryptionManager().encryptMessage(input.toString(), keys.publicKeyByteArray)
            encryptedResult.setText(encrypted)
        }

        val buttonDecrypt = findViewById<Button>(R.id.btnDecrypt)
        buttonDecrypt.setOnClickListener{
            val decrypted = EncryptionManager().decryptMessage(encryptedResult.text.toString(), keys.privateKeyByteArray.toBase64())
            decryptedResult.setText(decrypted)
        }
0

//        //others key and message
//        val decrypt = EncryptionManager().decryptMessage("J1WrM/pLathuT33s+SloSvqD3HZ1RRAf7XWucX7bHXbZz8XERImR+QmIoXqUHfGYZohXsnrrOiGuc/qZSqy+fWa5g/UMa8BKeFnQ9L8VoPMLjZZvN2XQs5zpCX+d/+IFhy73rtk7l3u0Iqpr+5TNIuGDH+4tSOFOUqDA5XbFHqXqSfkCErq1nxeHGRps4acfyklEA1THYDY7I9pFsqwBKPmoDGNgcLUQLL0awDPOBHsSwya84YQ+0IeJr6a/mmbElLESo2kYxWS3mjghIzEt/MWCiUdVXvZQdYGvLZJ2RN20r2Wv0oICrfMH4RppasEzsudjBN6VjpGkh/e93GJLow==",
//                "-----BEGIN RSA PRIVATE KEY-----MIIEpAIBAAKCAQEAh2VnqIEmHHqX8KVA7yEhzQWXsX6VvSMfGisVwzASWaP8bYEVkYSG/owlzJjhfFy30yswAwZQEp7Qt/rQRMpJErp3rREkqmKLYwULdZ19ZUyNuNzRMURkFASI8/aonXtSQyjtrurKTCS5OZehX6w1T/0r+B6n6v+mVnRhMMUJ+HY7pt3PO9RJuXbp+Gb2b8psBaAnqMMPkkBxUWjSswEx85s71oKYER1AJNVlKdnDxA/TDFaklgFre/z97z7zVdiIY4htwxoN5xnv1uFfvFufPiricURGYtKvrhe1epuAFI9bLuguZAJCH+cvQLb+KcHCqvfGAcaLQB6ipHx327hG6wIDAQABAoIBAGAmvldm5/a/hNlLsfJf+A4OE6zj9Vis8XB2+mxz3gQ0tZXaXsPJdsicJFfIlhDoE09uUxlCyWh/yj9taawAb6ZSL50DGKO7ebsnIP00i5F9rQOEJTc0l1Gz6sI9/35ezouNLEXUzTcHuVZR/TLhK7cTVr9Mnfj/9lNdMIwe0bbUxDMoVUIVKQtENlXpyecDLE5F7uucDfYZocRgdIzRBVdTmoXl8kNbpqHQrWE3T0AMAXHgOZwgTRvk9Pw3QpXIj7P7LJ6lJEYFFcZ9cHo1yK5BVVeYzjunceAgU/jZ7+NhW2JmAjvhwBMkUgnQuH5Zx3PKY9OCSfPcdLoM3kGgO7kCgYEA+XCJE4hbz5DOWnWETrz90vFnM66TgWjDsUbvqFDBS7Ezoa/l0WQ2fM5gbDFc2TxGXEY83O8+iua0yQWutiLJvqliFe2PdWDBs78mkRKtGvjFhZet8Elpji6op6f55ysn/vHLfG5Z9PVaBw2ELSwIRAKjLc0Mh8sdcodlYcZ3dXUCgYEAivUFMuUqYrD0Xvwkz5hH379PPXeCiHqqk2YKEmOVRbJ2M41gTAGo1SmQv8baWHNzOsCzdNg0udNKBFu26hNDkBhbo9BceAVy6G0wsdpdjK5hAMQZ9C/279/YjbWDD1B3PHJwAhYcUZsRZ7r2vOiGKNAtWEvATbac2BdnpTFoXt8CgYEAlzFrJPfGDq5LJfGNXNyZTb8dOupaZlpGI7JHmdI5F9Q045Bm0ODZVhURxQK2sMYkL9IzGge59R+Z/S8Ak03K01KALaJtDkyznGwEyHJ1kp+vuiYSPE+DN7d4awcaJtC10YZmPOn2hNAi3ZuHvomuCOtAVYawTa8EDmObRAS0a/ECgYA/mIf6lRti863D0w5d3En4wyvW691X7RwoPakaZW0p6tViHrAV2SSV+mH/A1lm5d+04gC/1zu6WFMlz3vVgV+IkT1PqcrIO2Ytrdt9an0qJ5zJpvZh5ZnUitQndw02cxM/HPjaW5g2WQwMN2s5ZgoQn8ZBBNpMNultlLigyA4GbQKBgQC0URVUeaQH5CWGW0NsQnCW1XGBp1EbP45dPzYLwgPlGDCVN1SHHSkCDUS5pqnBnYhdMlPCo6CotDUTnSczBAkmH8jrUj66CHc+/CFvJAnoqr9cfyO9iR+e35vl8oTZUJttZ7L4hdiNb0A6V7FbYnviwJWq7WtUsZVxP6fTy1nb/A==-----END RSA PRIVATE KEY-----")
//        println(decrypt)
    }
}