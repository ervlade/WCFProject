package com.alberto.wcfproject.utils

import com.alberto.wcfproject.data.model.Exercise
import com.alberto.wcfproject.data.model.SelectExercise

// Filtra los ejercicios según los CheckBox seleccionados
fun filterExercises(
    data: List<Exercise>,
    abdomenChecked: Boolean,
    backChecked: Boolean,
    bicepsChecked: Boolean,
    chestChecked: Boolean,
    legsChecked: Boolean,
    tricepsChecked: Boolean,
    shouldersChecked: Boolean,
): List<Exercise> {
    val filteredData = mutableListOf<Exercise>()

    if (
        !abdomenChecked &&
        !backChecked &&
        !bicepsChecked &&
        !chestChecked &&
        !legsChecked &&
        !tricepsChecked &&
        !shouldersChecked
    ) {
        filteredData += data
    } else {
        if (abdomenChecked) {
            filteredData += data.filter { it.muscleGroup == "abdomen" }
        }

        if (backChecked) {
            filteredData += data.filter { it.muscleGroup == "back" }
        }

        if (bicepsChecked) {
            filteredData += data.filter { it.muscleGroup == "biceps" }
        }

        if (chestChecked) {
            filteredData += data.filter { it.muscleGroup == "chest" }
        }

        if (legsChecked) {
            filteredData += data.filter { it.muscleGroup == "legs" }
        }

        if (tricepsChecked) {
            filteredData += data.filter { it.muscleGroup == "triceps" }
        }

        if (shouldersChecked) {
            filteredData += data.filter { it.muscleGroup == "shoulders" }
        }
    }

    return filteredData
}

fun filterSelectedExercises(
    data: List<SelectExercise>,
    abdomenChecked: Boolean,
    backChecked: Boolean,
    bicepsChecked: Boolean,
    chestChecked: Boolean,
    legsChecked: Boolean,
    tricepsChecked: Boolean,
    shouldersChecked: Boolean,
): List<SelectExercise> {
    val filteredData = mutableListOf<SelectExercise>()

    if (
        !abdomenChecked &&
        !backChecked &&
        !bicepsChecked &&
        !chestChecked &&
        !legsChecked &&
        !tricepsChecked &&
        !shouldersChecked
    ) {
        filteredData += data
    } else {
        if (abdomenChecked) {
            filteredData += data.filter { it.muscleGroup == "abdomen" }
        }

        if (backChecked) {
            filteredData += data.filter { it.muscleGroup == "back" }
        }

        if (bicepsChecked) {
            filteredData += data.filter { it.muscleGroup == "biceps" }
        }

        if (chestChecked) {
            filteredData += data.filter { it.muscleGroup == "chest" }
        }

        if (legsChecked) {
            filteredData += data.filter { it.muscleGroup == "legs" }
        }

        if (tricepsChecked) {
            filteredData += data.filter { it.muscleGroup == "triceps" }
        }

        if (shouldersChecked) {
            filteredData += data.filter { it.muscleGroup == "shoulders" }
        }
    }

    return filteredData
}

// Calcula el Índice de Masa Corporal (IMC) del usuario
fun calculateIMC(weight: Float, height: Int): Float {
    return weight / (height * 0.02f)
}