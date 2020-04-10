package com.andro.covid_19.ui.History

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.andro.covid_19.viewmodel.GalleryViewModel
import kotlinx.android.synthetic.main.fragment_history.*
import com.andro.covid_19.R


class HistoryFragment : Fragment() {

    private lateinit var galleryViewModel: GalleryViewModel

    override  fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_history, container, false)

afafaf

        return root
    }


}
