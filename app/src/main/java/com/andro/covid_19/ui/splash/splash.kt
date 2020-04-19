package com.andro.covid_19.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.andro.covid_19.R
import com.andro.covid_19.ui.instructions.instructions

class splash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
          startActivity(Intent(this@splash,instructions::class.java))
            finish()
        }, 1500)

       /* if (isNetworkConnected(this))
        {
            config = EasySplashScreen(this)
                .withFullScreen()
                .withTargetActivity(instructions::class.java)
                .withSplashTimeOut(3000)
                .withBackgroundColor(Color.parseColor("#3282b8"))
                .withAfterLogoText("Take only memories, leave only footprints. ")
                .withLogo(R.drawable.covid);
        }
        else {
            config =EasySplashScreen(this)
                .withFullScreen()
                .withTargetActivity( MainActivity::class.java)
                .withSplashTimeOut(3000)
                .withBackgroundColor(Color.parseColor("#0f4c75"))
                .withAfterLogoText("People don't take trips, trips take people.")
                .withLogo(R.drawable.covid);
        }
        config.getAfterLogoTextView().setTextColor(Color.WHITE);

        val easySplashScreen:View = config.create();
        setContentView(easySplashScreen);
    }*/
    }

}


