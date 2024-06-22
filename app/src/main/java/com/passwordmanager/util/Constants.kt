package com.passwordmanager.util

import javax.crypto.spec.SecretKeySpec


const val KEY = "mysecretkey12345"
val SECRET_KEY = SecretKeySpec(KEY.toByteArray(),"AES")

