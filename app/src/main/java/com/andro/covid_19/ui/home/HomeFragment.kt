package com.andro.covid_19.ui.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.andro.covid_19.R
import com.andro.covid_19.ui.map.MapFragment
import com.andro.retro.json_models.CountriesStat
import com.andro.retro.json_models.WorldTotalStates
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeAdapter: HomeAdapter
    private var Mapcountries: List<CountriesStat> = ArrayList<CountriesStat>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        HomeViewModel.context = this.context!!

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        setupObservers()
        setHasOptionsMenu(true)




        return root


    }

    private fun renderCountries(countries: List<CountriesStat>) {
        progress_bar.visibility = View.GONE
        homeAdapter = HomeAdapter(countries)
        Mapcountries = countries
        val layoutManger = LinearLayoutManager(getActivity())
        //layoutManger.stackFromEnd = true
        allCounties_recyclerview.layoutManager = layoutManger
        allCounties_recyclerview.adapter = homeAdapter
    }

    private fun renderWorldTotalStates(worldTotalStates: List<WorldTotalStates>) {
        tv_infected.text = worldTotalStates[0].total_cases
        tv_death.text = worldTotalStates[0].total_deaths
        tv_recovered.text = worldTotalStates[0].total_recovered
    }



    private fun setupObservers() {

        homeViewModel.getCountriesData().observe(viewLifecycleOwner, Observer<List<CountriesStat>> { renderCountries(it)
                   })
        GlobalScope.launch(Dispatchers.Main) {
             homeViewModel.getWorldTotalStates().observe(viewLifecycleOwner, Observer<List<WorldTotalStates>> { renderWorldTotalStates(it)
    })
}

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.map_button, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.mapButton) {
            val bundle = Bundle()
            val fragment: Fragment = MapFragment()
            val fragmentManager: FragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.nav_host_fragment, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()

        }
        return super.onOptionsItemSelected(item)
    }



}
