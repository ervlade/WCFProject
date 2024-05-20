package com.alberto.wcfproject.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SelectExercise(
    val uid: String,
    val name: String,
    val image: String,
    val muscleGroup: String,
    var selected: Boolean
): Parcelable