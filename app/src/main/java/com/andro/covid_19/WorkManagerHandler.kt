package com.andro.covid_19


import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
//import androidx.work.ListenableWorker
//import androidx.work.Worker
//import androidx.work.WorkerParameters
import com.andro.covid_19.data.api_services.ApiHandler
import com.andro.covid_19.data.api_services.ApiInterface
import com.andro.covid_19.data.network.ConnectivityInterceptorImpl
import com.andro.covid_19.ui.home.HomeFragment
import com.andro.covid_19.ui.home.HomeViewModel
import com.andro.covid_19.ui.settings.SettingsViewModel
import com.andro.retro.json_models.CountriesStat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/*class WorkManagerHandler(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        val api = ApiInterface(ConnectivityInterceptorImpl(SettingsViewModel.context))
        val apiHandler = ApiHandler(api)
        val countryName = inputData.getString(applicationContext.getString(R.string.country_name))

        GlobalScope.launch (Dispatchers.Main){
            countryName?.let { apiHandler.getSpecificCountryState(it) }
            Log.i("asmaa","om ahmed")
            apiHandler.specificCountryState.observeForever {
                createNotification("not", it.latest_stat_by_country[0].active_cases) }

        }


        return Result.success()
    }

    fun createNotification(title: String, text: String) {

        val manager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification =
            NotificationCompat.Builder(
                applicationContext,
                applicationContext.getString(R.string.channel_id)
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
}*/
