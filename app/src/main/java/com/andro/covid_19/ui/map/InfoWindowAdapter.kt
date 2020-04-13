package com.andro.covid_19.ui.map

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.andro.covid_19.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker


class InfoWindowAdapter (internal var context: Context?,  internal val cName: String?, internal val Ccases: String?, internal val Cdeath : String?,internal val Crecover : String?) : GoogleMap.InfoWindowAdapter {
    internal lateinit var inflater: LayoutInflater
    override fun getInfoContents(p0: Marker?): View? {
        return null
    }

    override fun getInfoWindow(p0: Marker?): View {
        inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = inflater.inflate(R.layout.info_window_layout, null)
        val title = v.findViewById(R.id.info_window_title) as TextView
        val cases = v.findViewById(R.id.info_window_cases) as TextView
        val death = v.findViewById(R.id.info_window_deaths) as TextView
        val recover = v.findViewById(R.id.info_window_recover) as TextView
        cases.text = "Cases: "+Ccases
        death.text = "Deaths : "+Cdeath
        recover.text = "Recovers : "+ Crecover


        return v

    }

}