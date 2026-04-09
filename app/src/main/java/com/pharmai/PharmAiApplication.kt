package com.pharmai


import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PharmAiApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}