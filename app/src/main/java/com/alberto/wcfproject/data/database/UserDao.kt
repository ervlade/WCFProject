package com.alberto.wcfproject.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.alberto.wcfproject.data.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user ")
    fun activeUser(): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertActiveUser(user: User)

    @Update
    fun updateActiveUser(user: User)
}