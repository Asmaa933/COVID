package com.andro.covid_19.ui.map

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.andro.covid_19.data.api_services.ApiHandler
import com.andro.covid_19.data.api_services.ApiInterface
import com.andro.covid_19.data.network.ConnectivityInterceptorImpl
import com.andro.covid_19.data.repositary.covidRepository
import com.andro.covid_19.data.repositary.covidRepositoryImpl
import com.andro.retro.json_models.CountriesStat

class MapViewModel : ViewModel() {
    companion object {
        lateinit var context: Context
    }
    private val api = ApiInterface(ConnectivityInterceptorImpl(context))
    private val apiHandler = ApiHandler(api)
    private var repository: covidRepository = covidRepositoryImpl(context,apiHandler)

    fun getCountriesData(): LiveData<List<CountriesStat>> = repository.getAllCountriesState()
}
