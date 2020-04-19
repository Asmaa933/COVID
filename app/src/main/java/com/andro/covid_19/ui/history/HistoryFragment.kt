package com.andro.covid_19.ui.history

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.andro.covid_19.R
import com.andro.covid_19.isNetworkConnected
import com.andro.retro.json_models.AllAffectedCountries
import com.andro.retro.json_models.HistoryOfCountry
import com.andro.retro.json_models.StatByCountry
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.android.synthetic.main.fragment_history.progress_bar
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class HistoryFragment : Fragment() {
    private var countryName: String = "USA"
    private var date: String? = null
    private  var oldData:HistoryOfCountry? = null
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
        val  searchButton = root.findViewById(R.id.searchButton) as Button
        val cardView = root.findViewById(R.id.cardView) as CardView
        val  CardLinearLayout = root.findViewById(R.id. CardLinearLayout) as LinearLayout

         searchButton.setOnClickListener {
             cardView.visibility = View.INVISIBLE
             CardLinearLayout.visibility = View.INVISIBLE
            if (isNetworkConnected(activity!!)) {
                progress_bar.visibility = View.VISIBLE
                if (date != null&&countryName!=null) {
                   historyViewModel.getHistoryForCountry(countryName!!, showDateTxt.text.toString()).observe(viewLifecycleOwner, Observer<HistoryOfCountry> {
                       Log.i("habl",it.toString() )
                       if (it != null) {
                                checkIfItIsOldData(it)

                            } else {
                                cardView.visibility = View.INVISIBLE
                                CardLinearLayout.visibility = View.INVISIBLE
                                progress_bar.visibility = View.INVISIBLE
                                Toast.makeText(activity!!, "no data", Toast.LENGTH_LONG).show()
                            }

                        })
                } else {
                    Toast.makeText(activity!!, "choose date for search", Toast.LENGTH_LONG)
                        .show()
                    progress_bar.visibility = View.INVISIBLE

                }
            } else {
                history_layout.visibility = View.INVISIBLE
                cardView.visibility = View.INVISIBLE
                CardLinearLayout.visibility = View.INVISIBLE
                no_connection.visibility = View.VISIBLE

            }

        }
       /* searchButton.setOnClickListener {
            cardView.visibility = View.INVISIBLE
            CardLinearLayout.visibility = View.INVISIBLE
            if (isNetworkConnected(activity!!)) {
                Log.i("habl", "ljhg")
                progress_bar.visibility = View.VISIBLE
                if (date != null && countryName != null) {
                    Log.i("habl", date + countryName)

                    val historyCountry = historyViewModel.getHistoryForCountry(
                        countryName!!,
                        showDateTxt.text.toString()
                    ).value
                    if (historyCountry != null) {
                        Log.i("habl", it.toString())
                        cardView.visibility = View.VISIBLE
                        CardLinearLayout.visibility = View.VISIBLE
                        newCasesTxt.text = historyCountry.stat_by_country.first().new_cases
                        newDeathsTxt.text = historyCountry.stat_by_country.first().new_deaths
                        recoverdTxt.text = historyCountry.stat_by_country.first().total_recovered
                        totalTxt.text = historyCountry.stat_by_country.first().total_cases
                        deathTxt.text = historyCountry.stat_by_country.first().total_deaths
                        progress_bar.visibility = View.INVISIBLE
                        Log.i("habl", "observe")
                    } else {
                        cardView.visibility = View.INVISIBLE
                        CardLinearLayout.visibility = View.INVISIBLE
                        progress_bar.visibility = View.INVISIBLE
                        Toast.makeText(activity!!, "no data", Toast.LENGTH_LONG).show()
                    }


                } else {
                    Toast.makeText(activity!!, "choose date for search", Toast.LENGTH_LONG)
                        .show()
                    progress_bar.visibility = View.INVISIBLE

                }
            } else {
                history_layout.visibility = View.INVISIBLE
                cardView.visibility = View.INVISIBLE
                CardLinearLayout.visibility = View.INVISIBLE
                no_connection.visibility = View.VISIBLE

            }


        }*/

            return root
    }
    fun checkIfItIsOldData(historyOfCountry:HistoryOfCountry)
    {
       GlobalScope.launch(Dispatchers.Main) {
           Log.i("habl","observe")
           progress_bar.visibility = View.INVISIBLE
           cardView.visibility = View.VISIBLE
           CardLinearLayout.visibility = View.VISIBLE
           newCasesTxt.text = historyOfCountry.stat_by_country.last().new_cases
           newDeathsTxt.text = historyOfCountry.stat_by_country.last().new_deaths
           recoverdTxt.text = historyOfCountry.stat_by_country.last().total_recovered
           totalTxt.text = historyOfCountry.stat_by_country.last().total_cases
           deathTxt.text = historyOfCountry.stat_by_country.last().total_deaths
       }


    }


    override fun onStart() {
        super.onStart()
        viewControl()
        historySwipeRefresh.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
        historySwipeRefresh.setOnRefreshListener {
            viewControl()

         }
    }

    private fun viewControl()
    {
        if (isNetworkConnected(activity!!)) {

            history_layout.visibility = View.VISIBLE
            no_connection.visibility = View.INVISIBLE
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
            historySwipeRefresh.isRefreshing = false

        }else
        {
            history_layout.visibility = View.INVISIBLE
            no_connection.visibility = View.VISIBLE
            cardView.visibility = View.INVISIBLE
            historySwipeRefresh.isRefreshing = false
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
        spinner.onItemSelectedListener = object:AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                countryName  = "USA"
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                    countryName = parent?.getItemAtPosition(position).toString()

            }

        }



        dateTxt.setOnClickListener {
            showDatePicker()

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



}
