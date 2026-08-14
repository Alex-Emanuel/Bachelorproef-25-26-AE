package com.example.dpdetectorapplication.services

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.dpdetectorapplication.R
import com.example.dpdetectorapplication.data.model.AnalysisResponse
import com.example.dpdetectorapplication.ui.MainActivity

object NotificationHelper {

    private const val CHANNEL_ID = "dark_pattern_detection"
    private const val CHANNEL_NAME = "Dark pattern detecties"

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID,CHANNEL_NAME,NotificationManager.IMPORTANCE_HIGH)
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }
    }

    fun showDarkPatternNotification(context: Context, response: AnalysisResponse) {
        // Enkel wanneer runtime permission toegestaan
        if (
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(context,Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        // Zijn notificaties globaal toegestaan?
        if (!NotificationManagerCompat.from(context).areNotificationsEnabled()) {
            return
        }

        val patterns = response.result.patterns

        val title = if (patterns.size == 1) {
            "Dark pattern gedetecteerd"
        } else {
            "${patterns.size} dark patterns gedetecteerd"
        }

        val text = patterns.joinToString(", ") { pattern -> pattern.name}

        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(text)
            .setStyle(NotificationCompat.BigTextStyle().bigText(text))
            .setContentIntent(pendingIntent)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        val notificationId = System.currentTimeMillis().toInt()

        NotificationManagerCompat
            .from(context)
            .notify(notificationId, notification)

        Log.d(
            "NotificationHelper",
            "Dark pattern notification verstuurd"
        )
    }
}