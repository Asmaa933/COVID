package com.andro.covid_19.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.andro.covid_19.R
import com.andro.covid_19.ui.instructions.instructions

class splash : AppCompatActivity() {
    protected var _active = true
    protected var _splashTime = 1500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
          startActivity(Intent(this@splash,instructions::class.java))
            finish()
        }, 1500)
    }
}


