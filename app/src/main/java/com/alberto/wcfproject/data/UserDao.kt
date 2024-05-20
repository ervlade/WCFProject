package com.alberto.wcfproject.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {

    @Query("SELECT * FROM user ")
    fun activeUser(): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertActiveUser(user: User)

    @Update
    fun updateActiveUser(user: User)


}


@Dao
interface ExerciseUserDao {
    @Query("SELECT * FROM ExerciseUser WHERE uid = :uid")
    fun getExerciseUserByUid(uid: String): ExerciseUser?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExerciseUser(exerciseUser: ExerciseUser)

    @Update
    fun updateExerciseUser(exerciseUser: ExerciseUser)
}