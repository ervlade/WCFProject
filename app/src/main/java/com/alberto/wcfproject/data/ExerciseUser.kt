package com.alberto.wcfproject.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ExerciseUser(
    @PrimaryKey val uid: String,
    @ColumnInfo val repetitions: Int?,
    @ColumnInfo val weight: Float?,
    @ColumnInfo val notes: String?
) {

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "repetitions" to repetitions,
            "weight" to weight,
            "notes" to notes
        )
    }
}