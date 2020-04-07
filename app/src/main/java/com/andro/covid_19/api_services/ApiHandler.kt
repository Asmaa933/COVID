package com.andro.covid_19.api_services

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.andro.covid_19.NoConnectivityException
import com.andro.retro.json_models.*

class ApiHandler(private val api: ApiInterface){

//get the random picture
private val _randomImage = MutableLiveData<Bitmap>()
    val randomImage: LiveData<Bitmap>
        get() = _randomImage
    suspend fun getRandomPicture() {
        var bitmap: Bitmap? = null
       try {
           val responseImg = api.getRandomInstruction().await()
           bitmap = BitmapFactory.decodeStream(responseImg.byteStream())
           _randomImage.postValue(bitmap)

       }catch (e: NoConnectivityException) {
           Log.e("Connectivity", "No internet connection.", e)
       }
    }

// get all affected country in array of String
    private val _effectedCountries = MutableLiveData<AllAffectedCountries>()
     val allAffectedCountries: LiveData<AllAffectedCountries>
        get() = _effectedCountries
     suspend fun getAffectedCountries()  {
        try {
            val fetchedCountries = api.getAffectedCountries().await()
            _effectedCountries.postValue(fetchedCountries)
        }
        catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection.", e)
        }
    }

//get cases for each country
    private val _allCountriesCases = MutableLiveData<CaseByCountry>()
    val allCountriesCases: LiveData<CaseByCountry>
        get() = _allCountriesCases
    suspend fun getCaseByCountry()  {
        try {
            val getCountriesCases = api.getCaseByCountry().await()
            _allCountriesCases.postValue(getCountriesCases)
        }
        catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection.", e)
        }
    }

// get country state
    private val _specificCountryState = MutableLiveData<SpecificCountryState>()
    val specificCountryState: LiveData<SpecificCountryState>
        get() = _specificCountryState
    suspend fun getSpecificCountryState(countryName: String)  {
        try {
            val getCountryState = api.getSpecificCountryState(countryName).await()
            _specificCountryState.postValue(getCountryState)
        }
        catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection.", e)

        }
    }

// get world state
    private val _worldState = MutableLiveData<WorldTotalStates>()
    val worldState: LiveData<WorldTotalStates>
        get() = _worldState
    suspend fun getWorldTotalState()  {
        try {
            val getWorldState = api.getWorldTotalState().await()
            _worldState.postValue(getWorldState)
        }
        catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection.", e)
        }
    }

// get history in specific date
    // date string format 2020-04-05
    private val _countryHistoryInDate = MutableLiveData<StatByCountry>()
    val countryHistoryInDate: LiveData<StatByCountry>
        get() = _countryHistoryInDate
    suspend fun getHistoryForCountryInDate(countryName: String, date: String)  {
        try {
            val getCountryHistory = api.getHistoryForCountryinDate(countryName,date).await()
            _countryHistoryInDate.postValue(getCountryHistory.stat_by_country.last())
        }
        catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection.", e)
        }
    }

}

