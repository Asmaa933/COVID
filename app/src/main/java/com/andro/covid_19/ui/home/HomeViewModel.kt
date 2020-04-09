package com.andro.covid_19.ui.home

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andro.covid_19.data.api_services.ApiHandler
import com.andro.covid_19.data.api_services.ApiInterface
import com.andro.covid_19.data.network.ConnectivityInterceptorImpl
import com.andro.covid_19.data.repositary.covidRepository
import com.andro.covid_19.data.repositary.covidRepositoryImpl
import com.andro.retro.json_models.CountriesStat
import com.andro.retro.json_models.StatByCountry
import com.andro.retro.json_models.WorldTotalStates
import kotlinx.coroutines.runBlocking

class HomeViewModel() : ViewModel() {

    companion object {
        lateinit var context:Context
    }

    private var repository: covidRepository =covidRepositoryImpl(context)
    fun getAllCountries()=repository.getSavedCountry()
    fun setCountry(StatByCountry: StatByCountry)=repository.saveCountry(StatByCountry)
    fun getCountriesData()=repository.getAllCountry()
    fun setCountryinDataBase(countriesStat: CountriesStat)=repository.saveCountriesStat(countriesStat)
    fun getWorldTotalStates()=repository.getWorldTotalStates()
    fun setWorldTotalStates(worldTotalStates: WorldTotalStates)=repository.addWorldTotalStates(worldTotalStates)


}