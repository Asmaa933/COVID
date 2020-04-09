package com.andro.covid_19.ui.home

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andro.covid_19.data.api_services.ApiHandler
import com.andro.covid_19.data.api_services.ApiInterface
import com.andro.covid_19.data.network.ConnectivityInterceptorImpl
import com.andro.covid_19.data.repositary.covidRepository
import com.andro.covid_19.data.repositary.covidRepositoryImpl
import com.andro.retro.json_models.StatByCountry
import kotlinx.coroutines.runBlocking

class HomeViewModel() : ViewModel() {

    companion object {
        lateinit var context:Context
    }

    private var repository: covidRepository =covidRepositoryImpl(context)
    fun getAllCountries()=repository.getSavedCountry()
    fun setCountry(StatByCountry: StatByCountry)=repository.saveCountry(StatByCountry)

   /* fun setCountry(StatByCountry: StatByCountry)
    {
        runBlocking {
            apiHandler.getCaseByCountry()
        }
        apiHandler.specificCountryState.observe(viewLifecycleOwner, Observer {
            //textView2.text = it.latest_stat_by_country[0].total_cases
            repository.saveCountry(StatByCountryarray)
       })

    }*/

    /*private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text*/
   /* class ViewModelFactory(application: Application): ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(HomeViewModel: Class<T>): T {
            return HomeViewModel.getConstructor(Application::class.java).newInstance(HomeViewModel)
        }
    }*/

}