package com.andro.covid_19

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build


class GlobalApplication: Application() {
companion object{
   private lateinit var globalContext: Context
    fun getApplicationContext(): Context{
        return globalContext
    }
}
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    globalContext = applicationContext
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel1 = NotificationChannel(
                applicationContext.getString(R.string.channel_id),
                applicationContext.getString(R.string.channel_id),
                NotificationManager.IMPORTANCE_HIGH
            )
            channel1.description = "This is Channel 1"
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel1)
        }
    }
}

