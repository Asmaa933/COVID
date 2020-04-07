package com.andro.retro.json_models

import androidx.room.Entity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "CountryState")
data class StatByCountry(
    @SerializedName("active_cases")
    @Expose
    val active_cases: String,
    @SerializedName("country_name")
    @Expose
    val country_name: String,
    @SerializedName("id")
    @Expose
   // @PrimaryKey
    val id: String,
    @SerializedName("new_cases")
    @Expose
    val new_cases: String,
    @SerializedName("new_deaths")
    @Expose
    val new_deaths: String,
    @SerializedName("record_date")
    @Expose
    val record_date: String,
    @SerializedName("region")
    @Expose
    val region:String,
    @SerializedName("serious_critical")
    @Expose
    val serious_critical: String,
    @SerializedName("total_cases")
    @Expose
    val total_cases: String,
    @SerializedName("total_cases_per1m")
    @Expose
    val total_cases_per1m: String,
    @SerializedName("total_deaths")
    @Expose
    val total_deaths: String,
    @SerializedName("total_recovered")
    @Expose
    val total_recovered: String
)