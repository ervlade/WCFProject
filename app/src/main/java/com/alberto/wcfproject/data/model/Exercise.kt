package com.alberto.wcfproject.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Exercise(
    val uid: String,
    val name: String,
    val image: String,
    val muscleGroup: String,
) : Parcelable