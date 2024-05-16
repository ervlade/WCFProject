package com.alberto.wcfproject.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class WCFDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}