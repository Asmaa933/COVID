package com.andro.covid_19.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.andro.covid_19.R
import com.andro.covid_19.data.api_services.ApiHandler
import com.andro.covid_19.data.api_services.ApiInterface
import com.andro.covid_19.data.network.ConnectivityInterceptorImpl
import com.andro.retro.json_models.CountriesStat
import com.andro.retro.json_models.StatByCountry
import com.andro.retro.json_models.WorldTotalStates
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.runBlocking

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeAdapter :HomeAdapter



    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        HomeViewModel.context = this!!.context!!

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        save()
        homeViewModel.getCountriesData().observe(viewLifecycleOwner, Observer<List<CountriesStat>> {this.renderCountries(it)
            })
       /* homeViewModel.getWorldTotalStates().observe(viewLifecycleOwner, Observer<List<WorldTotalStates>> {this.renderWorldTotalStates(it.last())
        })*/

            return root


    }

    private fun renderCountries(countries:List< CountriesStat>)
    {
        progress_bar.setVisibility(View.GONE)
        homeAdapter= HomeAdapter(countries)
        val layoutManger = LinearLayoutManager(getActivity())
        //layoutManger.stackFromEnd = true
        allCounties_recyclerview.layoutManager = layoutManger
        allCounties_recyclerview.adapter = homeAdapter
    }
    private fun renderWorldTotalStates(worldTotalStates:WorldTotalStates)
    {
        Log.d("myTag", ":WorldTotalStates")
        tv_infected.text = worldTotalStates.total_cases
        tv_death.text = worldTotalStates.total_deaths
        tv_recovered.text = worldTotalStates.total_recovered
    }
    private fun save()
    {
        val api = ApiInterface(ConnectivityInterceptorImpl(context!!))
        val apiHandler = ApiHandler(api)
        runBlocking {
            apiHandler.getCaseByCountry()
            apiHandler.getWorldTotalState()
        }
        apiHandler.allCountriesCases.observe(viewLifecycleOwner, Observer {
            Log.d("myTag", "kjhgf" + it.toString())
            val countries_stat = it.countries_stat
            Log.d("myTag", countries_stat.toString())
            for (i in 0..200) {
                homeViewModel.setCountryinDataBase(countries_stat[i])
            }
        })
        apiHandler.worldState.observe(viewLifecycleOwner, Observer {
            Log.d("myTag", it.toString())
                homeViewModel.setWorldTotalStates(it)

        })
        homeViewModel.getWorldTotalStates().observe(viewLifecycleOwner, Observer<List<WorldTotalStates>> {this.renderWorldTotalStates(it.last())
        })
    }


}
