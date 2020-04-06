package com.andro.covid_19.ui.instructions

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.andro.covid_19.MainActivity
import com.andro.covid_19.R

class instructions : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instructions)
        startActivity(Intent(this@instructions, MainActivity::class.java))
        finish()
    }
}
