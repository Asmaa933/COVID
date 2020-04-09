package com.andro.covid_19.data.repositary

import android.app.Application
import android.content.Context
import android.os.AsyncTask
import android.os.Message
import androidx.lifecycle.LiveData
import com.andro.covid_19.data.api_services.ApiHandler
import com.andro.covid_19.data.db.CountriesStatDao
import com.andro.covid_19.data.db.CountrystatDao
import com.andro.covid_19.data.db.CovidDataBase
import com.andro.covid_19.data.db.WorldTotalStatesDao
import com.andro.retro.json_models.CountriesStat
import com.andro.retro.json_models.StatByCountry
import com.andro.retro.json_models.WorldTotalStates
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class covidRepositoryImpl(context: Context): covidRepository,CoroutineScope
{
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private lateinit var CountrystatDao: CountrystatDao
    private lateinit var CountriesStatDao: CountriesStatDao
    private lateinit var WorldTotalStatesDao: WorldTotalStatesDao


    private val ApiHandler: ApiHandler? = null
   // private var AllStatByCountry:LiveData<List<StatByCountry>>


    init {
        val database: CovidDataBase? = CovidDataBase.getInstance(context)
        if (database != null) {
            CountrystatDao = database.CountrystatDao()!!
            CountriesStatDao = database.CountriesStatDao()!!
            WorldTotalStatesDao = database.WorldTotalStatesDao()!!

        }
       // AllStatByCountry = CountrystatDao.getAll()

    }
    override  fun getSavedCountry(): LiveData<List<StatByCountry>> {

            return CountrystatDao?.getAll()


    }

    override fun getAllCountry(): LiveData<List<CountriesStat>> {
        return  CountriesStatDao?.getAll()
    }

    override fun saveCountry(StatByCountry: StatByCountry) {
       // val insertUserAsyncTask = InsertUserAsyncTask(CountrystatDao).execute(StatByCountry)
        launch { InsertCountry(StatByCountry) }

    }

    override fun saveCountriesStat(countriesStat: CountriesStat) {
        launch { InsertCountry(countriesStat) }
    }

    override fun getWorldTotalStates(): LiveData<List<WorldTotalStates>> {
        return WorldTotalStatesDao?.getAll()
    }

    override fun addWorldTotalStates(WorldTotalStates: WorldTotalStates) {
        launch { InsertWorldTotalStates(WorldTotalStates) }
    }

    override fun searchCountry(query: String?): LiveData<List<StatByCountry?>?>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private suspend fun InsertCountry(StatByCountry: StatByCountry)
    {
        withContext(Dispatchers.IO)
        {
            CountrystatDao.insert(StatByCountry)
        }
    }
    private suspend fun InsertCountry(countriesStat: CountriesStat)
    {
        withContext(Dispatchers.IO)
        {
            CountriesStatDao.insert(countriesStat)
        }
    }
    private suspend fun InsertWorldTotalStates(worldTotalStates: WorldTotalStates)
    {
        withContext(Dispatchers.IO)
        {
            WorldTotalStatesDao.insert(worldTotalStates)
        }
    }


}