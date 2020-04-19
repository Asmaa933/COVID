package com.andro.covid_19.ui.home

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.andro.covid_19.R
import com.andro.covid_19.isNetworkConnected
import com.andro.covid_19.ui.map.MapFragment
import com.andro.retro.json_models.CountriesStat
import com.andro.retro.json_models.WorldTotalStates
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeAdapter: HomeAdapter
    private var Mapcountries: ArrayList<CountriesStat> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        HomeViewModel.context = this.context!!
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        setHasOptionsMenu(true)
        setupObserversBasedNatwork()
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(
            ContextCompat.getColor(
                context!!,
                R.color.colorPrimary
            )
        )
        swipeRefreshLayout.setOnRefreshListener {
            if (isNetworkConnected(activity!!)) {
                setupObserversBasedNatwork()
            } else {
                Snackbar.make(view!!, getString(R.string.check_connection), Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.action), null).show()
                swipeRefreshLayout.isRefreshing = false
            }


        }
    }

    private fun renderCountries(countries: List<CountriesStat>) {
        progress_bar.visibility = View.GONE
        homeAdapter = HomeAdapter(countries)
        Mapcountries = countries as ArrayList<CountriesStat>
        val layoutManger = LinearLayoutManager(getActivity())
        //layoutManger.stackFromEnd = true
        allCounties_recyclerview.layoutManager = layoutManger
        allCounties_recyclerview.adapter = homeAdapter
    }

    private fun renderWorldTotalStates(worldTotalStates: List<WorldTotalStates>) {
        if (worldTotalStates.isNotEmpty()) {
            tv_infected.text = worldTotalStates[0].total_cases
            tv_death.text = worldTotalStates[0].total_deaths
            tv_recovered.text = worldTotalStates[0].total_recovered
        }

    }


    private fun setupObserversBasedNatwork() {

        homeViewModel.getCountriesData().observe(viewLifecycleOwner, Observer<List<CountriesStat>> {
            renderCountries(it)
        })
        homeViewModel.getWorldTotalStates()
            .observe(viewLifecycleOwner, Observer<List<WorldTotalStates>> {
                renderWorldTotalStates(it)
            })
        GlobalScope.launch(Dispatchers.Main) {


            swipeRefreshLayout.isRefreshing = false
        }

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.map_button, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.mapButton) {
            val mapFragment: Fragment = MapFragment()
            val fragmentManager: FragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.nav_host_fragment, mapFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()

        }
        return super.onOptionsItemSelected(item)
    }


}
