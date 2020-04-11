package com.andro.covid_19.ui.History

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.andro.covid_19.R
import com.andro.retro.json_models.AllAffectedCountries
import com.andro.retro.json_models.StatByCountry
import kotlinx.android.synthetic.main.fragment_history.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class HistoryFragment : Fragment() {
    private var countryName: String? = null
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



        historyViewModel.getAllAffectedCountries()
            .observe(viewLifecycleOwner, Observer<AllAffectedCountries> {
                val array: ArrayList<String> = ArrayList()
                for (i in 0 until it.affected_countries.size - 1) {
                    if (!it.affected_countries[i].equals("")) {
                        array?.add(it.affected_countries[i])
                    }
                }
                array?.let { it1 -> setupSearch(it1) }

            })





        return root
    }


    private fun setupSearch(countriesArr: List<String>) {
        spinner.setTitle("Select country")
        spinner.setPositiveButton("OK");

        var adapter = ArrayAdapter(
            activity!!,
            R.layout.support_simple_spinner_dropdown_item,
            countriesArr
        )
        spinner.adapter = adapter
        spinner.setOnSearchTextChangedListener { countryName = it }


        searchButton.setOnClickListener {

            historyViewModel.getHistoryForCountry("USA", "2020-04-05").observe(viewLifecycleOwner, Observer<StatByCountry> {
                dateTxt.text = it.total_cases
            })


//            countryName?.let { it1 ->
//                historyViewModel.getHistoryForCountry("USA", "2020-04-05")
//                    .observe(viewLifecycleOwner, Observer<StatByCountry> {
//                        dateTxt.text = it.total_cases
//                    })



            dateTxt.setOnClickListener {
                showDatePicker()
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
                dateTxt.text = format.format(choosenDate.time)
            }, mYear, mMonth, mDay
        )
        datePickerDialog.setButton(
            DialogInterface.BUTTON_NEGATIVE,
            "cancel"
        ) { dialog, which ->
            if (which == DialogInterface.BUTTON_NEGATIVE) {

            }
        }
        datePickerDialog.show()

    }

}
