package com.alberto.wcfproject.utils

import android.content.Context
import com.alberto.wcfproject.R
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

// Filtra los ejercicios seleccionados según los grupos musculares marcados.
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

// Función para obtener el mensaje del IMC basado en el rango
fun getIMCMessage(context: Context, imc: Float): String {
    return when {
        imc < 18.5 -> context.getString(R.string.range_1)
        imc in 18.5..24.9 -> context.getString(R.string.range_2)
        imc in 25.0..29.9 -> context.getString(R.string.range_3)
        imc in 30.0..34.9 -> context.getString(R.string.range_4)
        imc in 35.0..39.9 -> context.getString(R.string.range_5)
        else -> context.getString(R.string.range_6)
    }
}