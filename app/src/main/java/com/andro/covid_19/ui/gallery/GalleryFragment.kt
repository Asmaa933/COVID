package com.andro.covid_19.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.andro.covid_19.R
import com.andro.covid_19.viewmodel.GalleryViewModel


class GalleryFragment : Fragment() {

    private lateinit var galleryViewModel: GalleryViewModel

    override  fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_gallery, container, false)
val api = ApiInterface(ConnectivityInterceptorImpl(context!!))
      val apiHandler = ApiHandler(api)

//        apiHandler.randomImage.observe(viewLifecycleOwner, Observer {
//            randomImg.setImageBitmap(it)
//        })
////
//        GlobalScope.launch(Dispatchers.Main) {
//            apiHandler.getRandomPicture()
//        }
//            apiHandler.getCaseByCountry()
//            apiHandler.getHistoryForCountryInDate("Egypt", "2020-04-04")
//            apiHandler.getWorldTotalState()
//            apiHandler.getAffectedCountries()
//            apiHandler.getSpecificCountryState("USA")
//        }

        return root
    }

}
