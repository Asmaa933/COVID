package com.andro.covid_19.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.andro.covid_19.R
import com.andro.covid_19.WorkManagerHandler
import com.andro.covid_19.isNetworkConnected
import com.andro.covid_19.ui.history.HistoryViewModel
import com.andro.covid_19.ui.home.HomeViewModel
import com.andro.retro.json_models.AllAffectedCountries
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.android.synthetic.main.fragment_settings.*
import java.time.Duration
import java.util.concurrent.TimeUnit

class SettingsFragment : Fragment() {
    private var countryName: String = "USA"
    private lateinit var settingsViewModel: SettingsViewModel
    private var chosenPeriod: String? = "2 hours"
    private var intervalTime: Long = 2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        SettingsViewModel.context = this.context!!
        settingsViewModel =
            ViewModelProviders.of(this).get(SettingsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_settings, container, false)

        return root
    }

    override fun onStart() {
        super.onStart()
        if (isNetworkConnected(activity!!)) {

            settingsViewModel.getAllAffectedCountries()
                .observe(viewLifecycleOwner, Observer<AllAffectedCountries> {
                    val array: ArrayList<String> = ArrayList()
                    for (i in 0 until it.affected_countries.size - 1) {
                        if (it.affected_countries[i] != "") {
                            array.add(it.affected_countries[i])
                        }
                    }
                    array.let { it1 -> setupCountrySpinner(it1) }

                })

        }
        setupIntervalSpinner()
        setupSaveButton()
    }

    fun setupIntervalSpinner() {
        intervalSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                chosenPeriod = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }

        }
    }

    fun setupCountrySpinner(countriesArr: List<String>) {
        notiCountry.setTitle("Select country")
        notiCountry.setPositiveButton("OK");

        var adapter = ArrayAdapter(
            activity!!,
            R.layout.my_spinner_item,
            countriesArr
        )
        notiCountry.adapter = adapter
        notiCountry.setOnSearchTextChangedListener { countryName = it }


    }

    fun setupSaveButton() {
        when(chosenPeriod){
            getString(R.string.two_hours) -> intervalTime = 2
            getString(R.string.one_hour) -> intervalTime = 1
            getString(R.string.five_hours) -> intervalTime = 5
            getString(R.string.once_day) -> intervalTime = 24

        }
        saveBtn.setOnClickListener {

            val data = Data.Builder().putString(getString(R.string.country_name), countryName).build()

            val request = PeriodicWorkRequestBuilder<WorkManagerHandler>(15, TimeUnit.MINUTES)
                .setInputData(data)
                    .build()

            WorkManager.getInstance().enqueueUniquePeriodicWork("key", ExistingPeriodicWorkPolicy.REPLACE, request)

        }
    }

}
