package com.andro.covid_19

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import com.google.gson.GsonBuilder

object AlarmManagerHandler {
    fun setAlarmManager(country: String, interval: Int) {
        val g= GsonBuilder()
        val alarmManager =
            GlobalApplication.getApplicationContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent =
            Intent(GlobalApplication.getApplicationContext(), NotificationReciever::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra(
            GlobalApplication.getApplicationContext().getString(R.string.country_name),
            country
        )
        val pendingIntent = PendingIntent.getBroadcast(
            GlobalApplication.getApplicationContext(),
            1,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        when (interval) {
            1 -> alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                SystemClock.elapsedRealtime(),
                AlarmManager.INTERVAL_HOUR,
                pendingIntent
            )
            2 -> alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP ,
                SystemClock.elapsedRealtime(),
                 2 *  60 * 1000//AlarmManager.INTERVAL_HOUR * 2,
                ,pendingIntent
            )

            5 -> alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                SystemClock.elapsedRealtime(),
                AlarmManager.INTERVAL_HOUR * 5,
                pendingIntent
            )
            24 -> alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                SystemClock.elapsedRealtime(),
                AlarmManager.INTERVAL_DAY,
                pendingIntent

            )

        }

    }

    fun cancelAlarm(country: String) {
        val alarmManager =
            GlobalApplication.getApplicationContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent =
            Intent(GlobalApplication.getApplicationContext(), NotificationReciever::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra(
            GlobalApplication.getApplicationContext().getString(R.string.country_name),
            country
        )
        val pendingIntent = PendingIntent.getService(
            GlobalApplication.getApplicationContext(),
            1,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        alarmManager.cancel(pendingIntent)
    }
}