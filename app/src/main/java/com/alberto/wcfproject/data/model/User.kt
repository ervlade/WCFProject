package com.alberto.wcfproject.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val uid: String,
    @ColumnInfo val email: String,
    @ColumnInfo val weight: Float,
    @ColumnInfo val height: Int,
) {
    fun toMap(): Map<String, Any> {
        return mapOf(
            "email" to email,
            "weight" to weight,
            "height" to height
        )
    }
}

