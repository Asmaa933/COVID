package com.andro.covid_19.ui.instructions

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.andro.covid_19.MainActivity
import com.andro.covid_19.R
import com.andro.covid_19.isNetworkConnected
import com.andro.covid_19.ui.home.HomeAdapter
import com.andro.covid_19.ui.home.HomeViewModel
import com.andro.retro.json_models.CountriesStat
import com.github.paolorotolo.appintro.AppIntro
import com.github.paolorotolo.appintro.AppIntroFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_home.*

class instructions : AppIntro() {

    private lateinit var instructionsViewModel:InstructionViewModel
    lateinit var img1:Bitmap
    lateinit var img2:Bitmap
    lateinit var img3:Bitmap



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        InstructionViewModel.context = this
       // setContentView(R.layout.activity_instructions)
        instructionsViewModel = ViewModelProviders.of(this).get(InstructionViewModel::class.java)

        addSlide(
           AppIntroFragment.newInstance("", "",
            R.drawable.first, ContextCompat.getColor(getApplicationContext(), R.color.home_color)))
         addSlide(AppIntroFragment.newInstance("", "",
            R.drawable.two, ContextCompat.getColor(getApplicationContext(), R.color.home_color)))
        addSlide(AppIntroFragment.newInstance("", "",
            R.drawable.three, ContextCompat.getColor(getApplicationContext(), R.color.home_color)))
        addSlide(AppIntroFragment.newInstance("", "",
            R.drawable.four, ContextCompat.getColor(getApplicationContext(), R.color.home_color)));
        addSlide(AppIntroFragment.newInstance("", "",
            R.drawable.five, ContextCompat.getColor(getApplicationContext(), R.color.home_color)));
        addSlide(AppIntroFragment.newInstance("", "",
            R.drawable.six, ContextCompat.getColor(getApplicationContext(), R.color.home_color)));

    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        startActivity(Intent(this@instructions, MainActivity::class.java))
        finish()
    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        startActivity(Intent(this@instructions, MainActivity::class.java))
        finish()
    }

    override fun onSlideChanged(oldFragment: Fragment?, newFragment: Fragment?) {
        super.onSlideChanged(oldFragment, newFragment)


    }





}
