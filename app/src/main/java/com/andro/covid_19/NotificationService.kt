package com.andro.covid_19

import android.app.NotificationManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.andro.covid_19.data.api_services.ApiHandler
import com.andro.covid_19.data.api_services.ApiInterface
import com.andro.covid_19.data.network.ConnectivityInterceptorImpl
import com.andro.covid_19.ui.settings.SettingsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.invoke
import kotlinx.coroutines.launch

class NotificationService: BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        val api = ApiInterface(ConnectivityInterceptorImpl(SettingsViewModel.context))
        val apiHandler = ApiHandler(api)
        val countryName =p1?.getStringExtra(GlobalApplication.getApplicationContext().getString(R.string.country_name))

        GlobalScope.launch (Dispatchers.Main){
            if (countryName != null) {
                apiHandler.getSpecificCountryState(countryName)
            }
            Log.i("asmaa","om ahmed")
            apiHandler.specificCountryState.observeForever {
                createNotification("not", it.latest_stat_by_country[0].active_cases) }

        }

    }

}


    fun createNotification(title: String, text: String) {

        val manager =
            GlobalApplication.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification =
            NotificationCompat.Builder(
                GlobalApplication.getApplicationContext(),
                GlobalApplication.getApplicationContext().getString(R.string.channel_id)
            )
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .build()

        manager.notify(1, notification)
    }

