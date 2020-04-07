package com.andro.covid_19.data.db

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.andro.retro.json_models.StatByCountry

@Database(
    entities = [StatByCountry::class],
    version = 1
)
abstract class CovidDataBase : RoomDatabase() {
    abstract fun CountrystatDao(): CountrystatDao?
//singlton
    companion object {
        private const val DB_NAME = "COVID_database"
    //THREATS will have immediate access to this property
        @Volatile private var INSTANCE: CovidDataBase? = null
    //dummy object to make sure that no two threats are currently doing the same thing
         private  val LOCK =Any()
    operator fun invoke(context: Context)= INSTANCE?: synchronized(LOCK)
    {
        INSTANCE?:buildDataBase(context).also { INSTANCE=it }
    }
    private fun buildDataBase(context: Context)= Room.databaseBuilder(
        context.applicationContext,
        CovidDataBase::class.java, DB_NAME
    ) .build()
        /*fun getInstance(application: Application?): CovidDataBase? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    application!!,
                    CovidDataBase::class.java, DB_NAME
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE
        }*/
    }
}
