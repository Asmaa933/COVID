package com.andro.covid_19.data.repositary

import androidx.lifecycle.LiveData
import com.andro.retro.json_models.StatByCountry

interface covidRepository {
    //want it to be asynchronous // enable us to call this function from a call routine
     fun getSavedCountry(): LiveData<List<StatByCountry>>

    fun saveCountry(movie: StatByCountry)

    fun deleteCountry(movie: StatByCountry?)

    fun searchCountry(query: String?): LiveData<List<StatByCountry?>?>?

}