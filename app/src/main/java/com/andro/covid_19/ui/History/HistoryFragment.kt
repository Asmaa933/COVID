package com.andro.covid_19.ui.History

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.andro.covid_19.R
import com.andro.covid_19.isNetworkConnected
import com.andro.retro.json_models.AllAffectedCountries
import com.andro.retro.json_models.StatByCountry
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class HistoryFragment : Fragment() {
    private var countryName: String = "USA"
    private var date: String? = null
    private lateinit var historyViewModel: HistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        HistoryViewModel.context = this.context!!

        historyViewModel =
            ViewModelProviders.of(this).get(HistoryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_history, container, false)
        return root
    }

    override fun onStart() {
        super.onStart()
        if (isNetworkConnected(activity!!)) {

            historyViewModel.getAllAffectedCountries()
                .observe(viewLifecycleOwner, Observer<AllAffectedCountries> {
                    val array: ArrayList<String> = ArrayList()
                    for (i in 0 until it.affected_countries.size - 1) {
                        countryName = it.affected_countries[1]
                        if (!it.affected_countries[i].equals("")) {
                            array?.add(it.affected_countries[i])
                        }
                    }
                    array?.let { it1 -> setupSearch(it1) }

                })

        }else
        {
            history_layout.visibility = View.INVISIBLE
            no_connection.visibility = View.VISIBLE
        }
    }




    private fun setupSearch(countriesArr: List<String>) {

        spinner.setTitle("Select country")
        spinner.setPositiveButton("OK");

        var adapter = ArrayAdapter(
            activity!!,
            R.layout.my_spinner_item,
            countriesArr
        )
        spinner.adapter = adapter

        spinner.setOnSearchTextChangedListener { countryName = it }


        dateTxt.setOnClickListener {
            showDatePicker()

        }


        GlobalScope.launch(Dispatchers.Main) {
            searchButton.setOnClickListener {
                if (isNetworkConnected(activity!!)) {
                    enableViews(false)
                    progress_bar.visibility = View.VISIBLE
                    if (date != null&&countryName!=null) {
                        historyViewModel.getHistoryForCountry(
                            countryName!!,
                            showDateTxt.text.toString()
                        )
                            .observe(viewLifecycleOwner, Observer<StatByCountry> {
                                if (it != null) {
                                    cardView.visibility = View.VISIBLE
                                    newCasesTxt.text = it.new_cases
                                    newDeathsTxt.text = it.new_deaths
                                    recoverdTxt.text = it.total_recovered
                                    totalTxt.text = it.total_cases
                                    deathTxt.text = it.total_deaths
                                    progress_bar.visibility = View.GONE
                                    enableViews(true)
                                } else {
                                    cardView.visibility = View.GONE
                                    Toast.makeText(activity!!, "no data", Toast.LENGTH_LONG).show()
                                }

                            })
                    } else {
                        Toast.makeText(activity!!, "choose date for search", Toast.LENGTH_LONG)
                            .show()
                        progress_bar.visibility = View.GONE
                        enableViews(true)
                    }
                } else {
                    history_layout.visibility = View.INVISIBLE

                    no_connection.visibility = View.VISIBLE

                }

            }

        }

    }

    private fun showDatePicker() {
        Locale.setDefault(Locale.ENGLISH)
        val choosenDate = Calendar.getInstance()
        val c = Calendar.getInstance()
        val mYear: Int = c.get(Calendar.YEAR)
        val mMonth: Int = c.get(Calendar.MONTH)
        val mDay: Int = c.get(Calendar.DAY_OF_MONTH)
        val format = SimpleDateFormat("yyyy-MM-dd")

        val datePickerDialog = DatePickerDialog(
            activity!!,
            OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                choosenDate.set(Calendar.YEAR, year)
                choosenDate.set(Calendar.MONTH, monthOfYear)
                choosenDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                date = format.format(choosenDate.time)
                showDateTxt.text = date
            }, mYear, mMonth, mDay
        )
        val cal = Calendar.getInstance()
        cal.set(2020, 2, 20)
        datePickerDialog.datePicker.minDate = cal.timeInMillis

        datePickerDialog.datePicker.maxDate = System.currentTimeMillis();
        datePickerDialog.show()

    }

    fun enableViews(flag: Boolean) {
        dateTxt.isEnabled = flag
        searchButton.isEnabled = flag
        spinner.isEnabled = flag

    }
}
