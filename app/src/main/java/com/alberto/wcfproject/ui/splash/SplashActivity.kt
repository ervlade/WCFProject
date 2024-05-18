package com.alberto.wcfproject.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.alberto.wcfproject.R
import com.alberto.wcfproject.data.WCFDatabase
import com.alberto.wcfproject.ui.home.HomeActivity
import com.alberto.wcfproject.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        validateUser()
    }
    // Valida al usuario y dirige a la actividad correspondiente después de un retraso de 2 segundos
    private fun validateUser() {
        Handler(Looper.getMainLooper()).postDelayed({
            val user = WCFDatabase.instance?.userDao()?.activeUser()

            if(user != null) {
                startActivity(Intent(this, HomeActivity::class.java))
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }, 2000)
    }
}