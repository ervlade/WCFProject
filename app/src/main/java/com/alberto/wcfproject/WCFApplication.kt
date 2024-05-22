package com.alberto.wcfproject

import android.app.Application
import com.alberto.wcfproject.data.database.WCFDatabase

//Realiza la inicializacion de la base de datos al iniciar la aplicacion
class WCFApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        WCFDatabase.createDatabase(this)
    }
}