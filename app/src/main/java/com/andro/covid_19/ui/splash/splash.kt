package com.andro.covid_19.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.andro.covid_19.R
import com.andro.covid_19.ui.instructions.instructions

class splash : AppCompatActivity() {
    protected var _active = true
    protected var _splashTime = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val splashTread: Thread = object : Thread() {
            override fun run() {
                try {
                    var waited = 0
                    while (_active && waited < _splashTime) {
                        sleep(100)
                        if (_active) {
                            waited += 100
                        }
                    }
                } catch (e: Exception) {
                } finally {
                    startActivity(Intent(this@splash,instructions::class.java))
                    finish()
                }
            }
        }
        splashTread.start()
    }
}

