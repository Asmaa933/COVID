package com.andro.covid_19.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.andro.retro.json_models.CountriesStat
@Dao
interface CountriesStatDao {
    @Query("SELECT * FROM CountriesStat")
    fun getAll(): LiveData<List<CountriesStat>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(CountriesStat: CountriesStat?)

    @Query("DELETE FROM CountriesStat")
    fun deleteAll()

    @Update
    fun updateMovie(CountriesStat: CountriesStat?)
}