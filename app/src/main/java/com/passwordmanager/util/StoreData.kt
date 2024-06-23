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
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, SECRET_KEY)
        val encryptBytes =cipher.doFinal(Gson().toJson(data).toByteArray(Charsets.UTF_8))
        return Base64.encodeToString(encryptBytes,Base64.DEFAULT)
    }

    @RequiresApi(Build.VERSION_CODES.FROYO)
    fun decrypt(string : String) : ArrayList<UserModel> {
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        cipher.init(Cipher.DECRYPT_MODE,SECRET_KEY)
        val decryptedBytes = cipher.doFinal(Base64.decode(string,Base64.DEFAULT))
        val jsonString = String(decryptedBytes,Charsets.UTF_8)
        return Gson().fromJson(jsonString, object : TypeToken<ArrayList<UserModel>>() {}.type)
    }

}
