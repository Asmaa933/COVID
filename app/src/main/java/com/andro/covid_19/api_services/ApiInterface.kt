package com.andro.covid_19.api_services

import com.andro.covid_19.network.ConnectivityInterceptor
import com.andro.retro.json_models.*
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiInterface {
    @Headers(
        "x-rapidapi-host: coronavirus-monitor.p.rapidapi.com",
        "x-rapidapi-key: 423421ec09mshbbc05ab5ac1e0dbp1dabddjsn196db168c54a")
    @GET("affected.php")
    fun getAffectedCountries(): Deferred<AllAffectedCountries>

    @Headers(
        "x-rapidapi-host: coronavirus-monitor.p.rapidapi.com",
        "x-rapidapi-key: 423421ec09mshbbc05ab5ac1e0dbp1dabddjsn196db168c54a")
    @GET("cases_by_country.php")
    fun getCaseByCountry(): Deferred<CaseByCountry>

    @Headers(
        "x-rapidapi-host: coronavirus-monitor.p.rapidapi.com",
        "x-rapidapi-key: 423421ec09mshbbc05ab5ac1e0dbp1dabddjsn196db168c54a")
    @GET("random_masks_usage_instructions.php")
    fun getRandomInstruction(): Deferred<ResponseBody>

    @Headers(
        "x-rapidapi-host: coronavirus-monitor.p.rapidapi.com",
        "x-rapidapi-key: 423421ec09mshbbc05ab5ac1e0dbp1dabddjsn196db168c54a")
    @GET("latest_stat_by_country.php")
    fun getSpecificCountryState(@Query("country") countryName: String): Deferred<SpecificCountryState>

    @Headers(
        "x-rapidapi-host: coronavirus-monitor.p.rapidapi.com",
        "x-rapidapi-key: 423421ec09mshbbc05ab5ac1e0dbp1dabddjsn196db168c54a")
    @GET("worldstat.php")
    fun getWorldTotalState(): Deferred<WorldTotalStates>

    @Headers(
        "x-rapidapi-host: coronavirus-monitor.p.rapidapi.com",
        "x-rapidapi-key: 423421ec09mshbbc05ab5ac1e0dbp1dabddjsn196db168c54a")
    @GET("history_by_particular_country_by_date.php")
    fun getHistoryForCountryinDate(@Query("country") countryName: String,
                                   @Query("date") date: String): Deferred<HistoryOfCountry>


    companion object {
        operator fun invoke(connectivityInterceptor: ConnectivityInterceptor): ApiInterface {
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()
                return@Interceptor chain.proceed(request)
            }
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://coronavirus-monitor.p.rapidapi.com/coronavirus/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterface::class.java)
        }
    }

}