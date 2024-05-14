package com.alberto.wcfproject.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class WCFDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {

        var instance: WCFDatabase? = null

        fun createDatabase(context: Context) {
            if(instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    WCFDatabase::class.java,
                    "wcf_database.db"
                ).allowMainThreadQueries().build()
            }
        }
    }
}