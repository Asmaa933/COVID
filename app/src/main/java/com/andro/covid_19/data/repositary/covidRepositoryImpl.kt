package com.andro.covid_19.data.repositary

import android.app.Application
import android.content.Context
import android.os.AsyncTask
import android.os.Message
import androidx.lifecycle.LiveData
import com.andro.covid_19.data.api_services.ApiHandler
import com.andro.covid_19.data.db.CountrystatDao
import com.andro.covid_19.data.db.CovidDataBase
import com.andro.retro.json_models.StatByCountry
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
    private val ApiHandler: ApiHandler? = null
   // private var AllStatByCountry:LiveData<List<StatByCountry>>


    init {
        val database: CovidDataBase? = CovidDataBase.getInstance(context)
        if (database != null) {
            CountrystatDao = database.CountrystatDao()!!
        }
       // AllStatByCountry = CountrystatDao.getAll()

    }
    override  fun getSavedCountry(): LiveData<List<StatByCountry>> {

            return CountrystatDao?.getAll()


    }

    override fun saveCountry(StatByCountry: StatByCountry) {
       // val insertUserAsyncTask = InsertUserAsyncTask(CountrystatDao).execute(StatByCountry)
        launch { InsertCountry(StatByCountry) }

    }

    override fun deleteCountry(movie: StatByCountry?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun searchCountry(query: String?): LiveData<List<StatByCountry?>?>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    /*private class InsertUserAsyncTask(CountrystatDao:CountrystatDao) : AsyncTask<StatByCountry, Unit, Unit>() {
        val CountrystatDao = CountrystatDao

        override fun doInBackground(vararg p0: StatByCountry?) {
            CountrystatDao.insert(p0[0]!!)
        }
    }*/
    private suspend fun InsertCountry(StatByCountry: StatByCountry)
    {
        withContext(Dispatchers.IO)
        {
            CountrystatDao.insert(StatByCountry)
        }
    }


}