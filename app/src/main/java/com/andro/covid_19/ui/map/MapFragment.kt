package com.andro.covid_19.ui.map

import android.content.Context
import android.location.Address
import android.location.Geocoder
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
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.map_fragment.*

class MapFragment : Fragment(), OnMapReadyCallback {

    companion object {
        fun newInstance() = MapFragment()
    }

    private lateinit var viewModel: MapViewModel
    private lateinit var googleMap: GoogleMap



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root=  inflater.inflate(R.layout.map_fragment, container, false)
        MapViewModel.context = this.context!!
        viewModel = ViewModelProviders.of(this).get(MapViewModel::class.java)
        viewModel.getCountriesData().observe(viewLifecycleOwner, Observer<List<CountriesStat>> {
            putEfectedCountries(it,it.indices)


        })
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
    }
    fun putEfectedCountries(countries: List<CountriesStat>,size:IntRange)
    {

        for(i in size)
        {
            Log.i("my",countries[i].country_name)
           var gc = Geocoder(context?.applicationContext)
            val addresses: List<Address> = try {
            gc.getFromLocationName(countries[i].country_name, 3)
        } catch (e: Exception) {
                ArrayList<Address>()
        }
            if (addresses.isNotEmpty()){
                //var title:String = "Country Name : "+countries[i].country_name+"\nConfirm : "+countries[i].cases+"\nDeath : "+countries[i].deaths+"\nRecover : "+countries[i].total_recovered
                    googleMap.addMarker(MarkerOptions().position(LatLng(addresses.get(addresses.size-1).latitude, addresses.get(addresses.size-1).longitude)).title("Country Name : "+countries[i].country_name ).snippet("Confirm : "+countries[i].cases))

                }


        }

    }

}


