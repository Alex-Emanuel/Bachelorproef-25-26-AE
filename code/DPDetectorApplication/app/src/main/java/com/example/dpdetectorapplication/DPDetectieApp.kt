package com.example.dpdetectorapplication

import android.app.Application
import com.example.dpdetectorapplication.services.NotificationHelper
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DPDetectieApp : Application() {
    override fun onCreate() {
        super.onCreate()

        NotificationHelper.createNotificationChannel(this)
    }
}