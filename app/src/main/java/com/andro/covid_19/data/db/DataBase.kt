package com.andro.covid_19.data.db

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.andro.retro.json_models.CountriesStat
import com.andro.retro.json_models.StatByCountry
import kotlinx.coroutines.CoroutineScope

@Database(
    entities = [StatByCountry::class, CountriesStat::class],
    version = 1
)
abstract class CovidDataBase : RoomDatabase() {
    abstract fun CountrystatDao(): CountrystatDao?
    abstract fun CountriesStatDao(): CountriesStatDao?
    companion object {

        private const val DB_NAME = "COVID_database"
    //THREATS will have immediate access to this property
        @Volatile private var INSTANCE: CovidDataBase? = null
        fun  getInstance(context: Context): CovidDataBase? {
            if (INSTANCE == null) {
                synchronized(CovidDataBase::class.java){
                    if(INSTANCE == null){
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            CovidDataBase::class.java, DB_NAME
                        )
                            .allowMainThreadQueries()
                            .build()
                    }
                }

            }
            return INSTANCE
        }
    }
}
