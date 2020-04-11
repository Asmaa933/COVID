package com.andro.covid_19.data.repositary

import androidx.lifecycle.LiveData
import com.andro.retro.json_models.AllAffectedCountries
import com.andro.retro.json_models.CountriesStat
import com.andro.retro.json_models.StatByCountry
import com.andro.retro.json_models.WorldTotalStates

interface covidRepository {
    //want it to be asynchronous // enable us to call this function from a call routine

    fun getAllCountriesState(): LiveData<List<CountriesStat>>
    fun saveCountriesStat(CountriesStat: CountriesStat)

    fun getWorldTotalStates(): LiveData<List<WorldTotalStates>>
    fun addWorldTotalStates(WorldTotalStates:WorldTotalStates)

    fun getHistoryForCountry(countryName: String, date: String): LiveData<StatByCountry>
    fun getAffectedCountries(): LiveData<AllAffectedCountries>



}