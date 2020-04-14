package com.andro.covid_19.ui.map

import android.location.Address
import android.location.Geocoder
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.andro.covid_19.R
import com.andro.retro.json_models.CountriesStat
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.map_fragment.*


class MapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMapLoadedCallback, GoogleMap.OnInfoWindowClickListener {

    companion object {
        fun newInstance() = MapFragment()
    }

    private lateinit var viewModel: MapViewModel
    private lateinit var googleMap: GoogleMap



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root=  inflater.inflate(R.layout.map_fragment, container, false)
        MapViewModel.context = this.context!!
        viewModel = ViewModelProviders.of(this).get(MapViewModel::class.java)



        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        mapView.getMapAsync(this)



    }

    override fun onMapReady(map: GoogleMap?) {
        map?.let {
            googleMap = it
        }
        googleMap.setOnMapLoadedCallback(this)


    }

    private fun putEffectedCountries(countries: List<CountriesStat>, size:IntRange) {

        for (i in size) {
            putEfectedCountriesAsync().execute(countries[i])
        }
    }
            /*private fun putEfectedCountries(countries: List<CountriesStat>, size:IntRange)
             {

                     for (i in size) {

                               Log.i("my", countries[i].country_name)
                              var gc = Geocoder(context?.applicationContext)
                              val addresses: List<Address> = try {
                               gc.getFromLocationName(countries[i].country_name, 3)
                                } catch (e: Exception) {
                                       ArrayList<Address>()
                                }
                             if (addresses.isNotEmpty()) {
                             //var title:String = "Country Name : "+countries[i].country_name+"\nConfirm : "+countries[i].cases+"\nDeath : "+countries[i].deaths+"\nRecover : "+countries[i].total_recovered

                                 googleMap.addMarker(
                                     MarkerOptions().position(
                                         LatLng(
                                             addresses.get(addresses.size - 1).latitude,
                                             addresses.get(addresses.size - 1).longitude
                                         )
                                     ).title("Country Name : " + countries[i].country_name).snippet("Confirm : " + countries[i].cases)
                                 )
                             }





                     }


             }*/
       /* suspend fun lengthyOperation(country_name:String): LatLng?
        {
            var gc = Geocoder(context?.applicationContext)
            val addresses: List<Address> = try {
                gc.getFromLocationName(country_name, 3)
            } catch (e: Exception) {
                ArrayList<Address>()
            }
            if (addresses.isNotEmpty()) {
                 return LatLng(
                    addresses.get(addresses.size - 1).latitude,
                    addresses.get(addresses.size - 1).longitude
                )
            }
            return null
        }*/



    override fun onMapLoaded() {
                viewModel.getCountriesData()
                    .observe(viewLifecycleOwner, Observer<List<CountriesStat>> {

                            putEffectedCountries(it, it.indices)

                    })

    }
    private inner class putEfectedCountriesAsync : AsyncTask<CountriesStat, Void,  List<Address>>(){

        var CountriesStat:CountriesStat? = null
        override fun doInBackground(vararg params: CountriesStat?): List<Address> {
            var gc = Geocoder(context?.applicationContext)
            val addresses: List<Address> = try {
                gc.getFromLocationName(params[0]?.country_name, 3)
            } catch (e: Exception) {
                ArrayList<Address>()
            }
            CountriesStat = params[0]
            return  addresses
        }


        override fun onPostExecute(result: List<Address>) {
            super.onPostExecute(result)
            if (result.isNotEmpty()) {
                googleMap.setInfoWindowAdapter( InfoWindowAdapter(context?.applicationContext))
              //  var snippet:String = "\nConfirm : "+CountriesStat?.cases+"\nDeath : "+CountriesStat?.deaths+"\nRecover : "+CountriesStat?.total_recovered
                googleMap.addMarker(
                    MarkerOptions().position(
                        LatLng(
                            result.get(result.size - 1).latitude,
                            result.get(result.size - 1).longitude
                        )
                    ).title(CountriesStat?.cases).snippet(CountriesStat?.deaths+","+CountriesStat?.total_recovered)
                )
            }
        }


    }

    override fun onInfoWindowClick(p0: Marker?) {

    }

}


