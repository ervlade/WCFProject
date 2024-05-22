package com.alberto.wcfproject.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alberto.wcfproject.data.model.User

@Database(entities = [User::class], version = 1)
abstract class WCFDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {

        var instance: WCFDatabase? = null

        //Crea una instancia de la base de datos si no existe una previamente creada
        fun createDatabase(context: Context) {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    WCFDatabase::class.java,
                    "wcf_database.db"
                ).allowMainThreadQueries().build()
            }
        }
    }
}