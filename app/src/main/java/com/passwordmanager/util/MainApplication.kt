package com.passwordmanager.util


import android.app.Application

class MainApplication : Application() {

    companion object {
        lateinit var instance: MainApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}