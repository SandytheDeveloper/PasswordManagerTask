package com.passwordmanager.util

import android.os.Build
import android.util.Base64
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.passwordmanager.model.UserModel
import javax.crypto.Cipher

object StoreData {

    @RequiresApi(Build.VERSION_CODES.FROYO)
    fun encrypt(data: ArrayList<UserModel>) : String{
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding") //Specifying which mode of AES is to be used
        cipher.init(Cipher.ENCRYPT_MODE, SECRET_KEY)// Specifying the mode wither encrypt or decrypt
        val encryptBytes =cipher.doFinal(Gson().toJson(data).toByteArray(Charsets.UTF_8))//Converting the string that will be encrypted to byte array
        return Base64.encodeToString(encryptBytes,Base64.DEFAULT) // returning the encrypted string
    }

    @RequiresApi(Build.VERSION_CODES.FROYO)
    fun decrypt(string : String) : ArrayList<UserModel> {
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        cipher.init(Cipher.DECRYPT_MODE,SECRET_KEY)
        val decryptedBytes = cipher.doFinal(Base64.decode(string,Base64.DEFAULT)) // decoding the entered string
        val jsonString = String(decryptedBytes,Charsets.UTF_8) // returning the decrypted string
        return Gson().fromJson(jsonString, object : TypeToken<ArrayList<UserModel>>() {}.type)
    }

}
