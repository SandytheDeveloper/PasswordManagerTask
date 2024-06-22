package com.passwordmanager.util

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.passwordmanager.R
import java.security.SecureRandom


object Util {


    fun print(message: String) {
        if (message.length > 1000) {
            val maxLogSize = 1000
            for (i in 0..message.length / maxLogSize) {
                val start = i * maxLogSize
                var end = (i + 1) * maxLogSize
                end = if (end > message.length) message.length else end
                Log.v("Print ::", message.substring(start, end))
            }
        } else {
            Log.i("Print ::", message)
        }
    }


    fun showToastMessage(context: Context, mgs: String, isShort: Boolean) {
        Toast.makeText(
            context,
            mgs,
            if (isShort) Toast.LENGTH_SHORT else Toast.LENGTH_SHORT
        ).show()
    }

    fun checkCondition(context: Context,accountType : String, email :String, password : String) : Boolean {

        return when {
            accountType.isEmpty() -> {
                showToastMessage(context ,"Account Name is required",true)
                false
            }
            email.isEmpty() -> {
                showToastMessage(context ,"Email is required",true)
                false
            }
            password.isEmpty() -> {
                showToastMessage(context ,"Password is required",true)
                false
            }
            else -> true
        }

    }

    fun authenticationCheck(
        activity: Activity,
        biometricResult: BiometricPromptManager.BiometricResult?
    ) {

        biometricResult?.let { result ->

            when (result) {
                is BiometricPromptManager.BiometricResult.AuthenticationError -> {
                    print("Authentication error : ${result.error}")
                    showToastMessage(activity,result.error,true)
                    activity.finishAffinity()
                }

                BiometricPromptManager.BiometricResult.AuthenticationFailed -> {
                    print("Authentication failed")
                }

                BiometricPromptManager.BiometricResult.AuthenticationNotSet -> {
                    print("Authentication not set")
                }

                BiometricPromptManager.BiometricResult.AuthenticationSuccess -> {
                    print("Authentication success")
                }

                BiometricPromptManager.BiometricResult.FeatureUnavailable -> {
                    print("Feature unavailable")
                }

                BiometricPromptManager.BiometricResult.HardwareUnavailable -> {
                    print("Hardware unavailable")
                }
            }


        }
    }

    fun checkPasswordStrength(password: String, onResult : (String,Int) -> Unit) {

        if (password.isEmpty()){
            onResult.invoke("",R.color.button_red)
            return
        }

        val length = password.length
        val set: Set<Char> = HashSet(mutableListOf('!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '+'))

        val hasLower = password.any { it.isLowerCase() }
        val hasUpper = password.any { it.isUpperCase() }
        val hasDigit = password.any { it.isDigit() }
        val specialChar = password.any { set.contains(it) }

        if (hasLower && hasUpper && hasDigit && specialChar && length >= 8){
            onResult.invoke("Strong",R.color.green)
        } else if (hasLower && hasUpper && specialChar && length >= 6) {
            onResult.invoke("Medium",R.color.yellow)
        } else {
            onResult.invoke("Weak",R.color.button_red)
        }
    }

    fun generatePassword() :String {

        val charSet = ('A'..'Z') + ('a'..'z') + ('0'..'9') + listOf('!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '+')
        val random = SecureRandom()
        return (1..10)
            .map { charSet[random.nextInt(charSet.size)] }
            .joinToString("")
    }

}


