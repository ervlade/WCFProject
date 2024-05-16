package com.alberto.wcfproject

import android.app.Application
import androidx.room.Room
import com.alberto.wcfproject.data.WCFDatabase

//Realiza la inicializacion de la base de datos al iniciar la aplicacion
class WCFApplication : Application() {

    val database by lazy {
        Room.databaseBuilder(
            this,
            WCFDatabase::class.java,
            "wcf_database.db"
        ).allowMainThreadQueries().build()
    }
}