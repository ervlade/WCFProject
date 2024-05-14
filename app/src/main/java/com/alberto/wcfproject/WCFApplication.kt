package com.alberto.wcfproject

import android.app.Application
import com.alberto.wcfproject.data.WCFDatabase

class WCFApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        WCFDatabase.createDatabase(this)
    }
}