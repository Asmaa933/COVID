package com.andro.covid_19

import android.content.SharedPreferences
import com.andro.retro.json_models.LatestStatByCountry

object SavedPreferences {
fun saveLastDataForCountry(lastStatus: LatestStatByCountry){

    val settings: SharedPreferences = GlobalApplication.getApplicationContext().getSharedPreferences(GlobalApplication.getApplicationContext().getString(R.string.country_data),0)
    val editor = settings.edit()
    editor.putString(GlobalApplication.getApplicationContext().getString(R.string.total_cases), lastStatus.total_cases)
    editor.putString(GlobalApplication.getApplicationContext().getString(R.string.country_name), lastStatus.country_name)
    editor.putString(GlobalApplication.getApplicationContext().getString(R.string.total_cases), lastStatus.total_cases)
    editor.putString(GlobalApplication.getApplicationContext().getString(R.string.total_cases), lastStatus.total_cases)
    editor.commit()
}

}