package com.andro.covid_19.ui.History

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.andro.covid_19.data.api_services.ApiHandler
import com.andro.covid_19.data.api_services.ApiInterface
import com.andro.covid_19.data.network.ConnectivityInterceptorImpl
import com.andro.covid_19.data.repositary.covidRepository
import com.andro.covid_19.data.repositary.covidRepositoryImpl
import com.andro.retro.json_models.AllAffectedCountries
import com.andro.retro.json_models.CountriesStat
import com.andro.retro.json_models.StatByCountry

class HistoryViewModel : ViewModel() {

    companion object {
        lateinit var context: Context
    }
    private val api = ApiInterface(ConnectivityInterceptorImpl(context))
    private val apiHandler = ApiHandler(api)

    private var repository: covidRepository = covidRepositoryImpl(context,apiHandler)

    fun getHistoryForCountry(countryName: String, date: String): LiveData<StatByCountry> = repository.getHistoryForCountry(countryName,date)
    fun getAllAffectedCountries(): LiveData<AllAffectedCountries>  = repository.getAffectedCountries()

}