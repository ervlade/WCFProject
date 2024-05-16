package com.alberto.wcfproject.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.alberto.wcfproject.R
import com.alberto.wcfproject.WCFApplication
import com.alberto.wcfproject.ui.home.HomeActivity
import com.alberto.wcfproject.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        validateUser()
    }

    private fun validateUser() {
        Handler(Looper.getMainLooper()).postDelayed({
            val user = (application as WCFApplication).database.userDao().getAll().firstOrNull()

            if(user != null) {
                startActivity(Intent(this, HomeActivity::class.java))
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }, 2000)
    }
}