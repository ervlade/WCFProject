package com.alberto.wcfproject.utils

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import com.alberto.wcfproject.R
import com.alberto.wcfproject.data.database.WCFDatabase
import com.alberto.wcfproject.data.model.Exercise
import com.alberto.wcfproject.data.model.ExerciseUser
import com.alberto.wcfproject.data.model.Routine
import com.alberto.wcfproject.data.model.SelectExercise
import com.alberto.wcfproject.data.model.User
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import java.util.UUID

fun getFirebaseImageReference(imagePath: String): StorageReference {
    return Firebase.storage.reference.child(imagePath)
}

fun collectExerciseDataByUserFromFirestore(userId: String, exerciseUid: String): ExerciseUser? {
    return runBlocking {
        val result = FirebaseFirestore.getInstance().collection("exercises_user").document(userId)
            .collection("exercises").document(exerciseUid).get().await()
        var exerciseUser: ExerciseUser? = null

        val weight = result.getDouble("weight")?.toFloat()
        val repetitions = result.getLong("repetitions")?.toInt()
        val notes = result.getString("notes")

        println("weight: " + weight)
        println("repetitions: " + repetitions)
        println("notes: " + notes)
        if (weight != null || repetitions != null || notes != null) {
            exerciseUser = ExerciseUser(
                uid = result.id,
                weight = weight,
                repetitions = repetitions,
                notes = notes
            )
        }

        exerciseUser
    }
}

fun saveExerciseDataByUserInFirestore(context: Activity, userId: String, exercise: ExerciseUser, create: Boolean) {
    if (!create) {
        FirebaseFirestore.getInstance().collection("exercises_user").document(userId)
            .collection("exercises").document(exercise.uid)
            .update(exercise.toMap())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.common_date_saved_successfully),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    context.finish()
                } else {
                    Toast.makeText(
                        context,
                        context.getString(R.string.common_error_saving_to_database),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    } else {
        FirebaseFirestore.getInstance().collection("exercises_user").document(userId)
            .collection("exercises").document(exercise.uid).set(exercise.toMap())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.common_date_saved_successfully),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    context.finish()
                } else {
                    Toast.makeText(
                        context,
                        context.getString(R.string.common_error_saving_to_database),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}

// Recolecta los ejercicios de la base de datos Firestore
fun collectExercises(): List<Exercise> {
    return runBlocking {
        val result = FirebaseFirestore.getInstance().collection("exercises").get().await()
        val data = mutableListOf<Exercise>()

        for (document in result.documents) {
            val exercise = Exercise(
                document.id,
                document.getString("name") ?: "",
                document.getString("imageUrl") ?: "",
                document.getString("muscleGroup") ?: ""
            )

            data.add(exercise)
        }

        data.sortWith(compareBy({ it.muscleGroup }, { it.name }))
        data
    }
}

fun collectExercises(exercisesIds: List<String>): List<Exercise> {
    return runBlocking {
        val data = mutableListOf<Exercise>()

        exercisesIds.forEach {
            val result =
                FirebaseFirestore.getInstance().collection("exercises").document(it).get().await()

            val exercise = Exercise(
                result.id,
                result.getString("name") ?: "",
                result.getString("imageUrl") ?: "",
                result.getString("muscleGroup") ?: ""
            )

            data.add(exercise)
        }

        data
    }
}

fun saveUserData(context: Activity, user: User?) {
    if (user != null) {
        WCFDatabase.instance?.userDao()?.updateActiveUser(user)
        FirebaseFirestore.getInstance().collection("users").document(user.uid)
            .update(user.toMap())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.profile_screen_registrate),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        context,
                        context.getString(R.string.common_register_fail),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}

fun saveRoutineDataByUserInFirestore(
    context: Activity,
    userId: String,
    routineName: String,
    routineExercises: List<SelectExercise>,
) {
    val routineData = Routine(
        id = UUID.randomUUID().toString(),
        name = routineName,
        exercises = routineExercises.map { it.uid }
    )

    FirebaseFirestore.getInstance().collection("routines").document(userId)
        .collection("routines").document().set(routineData.toMap())
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(
                    context,
                    context.getString(R.string.common_date_saved_successfully),
                    Toast.LENGTH_SHORT
                ).show()
                val returnIntent = Intent().apply {
                    putExtra(context.getString(R.string.routine_create_screen_routine_add), true)
                }

                context.setResult(Activity.RESULT_OK, returnIntent)
                context.finish()
            } else {
                Toast.makeText(
                    context,
                    context.getString(R.string.common_error_saving_to_database),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
}

fun collectRoutines(): List<Routine> {
    return runBlocking {
        val result =
            FirebaseFirestore.getInstance().collection("routines")
                .document(Firebase.auth.uid.toString()).collection("routines").get().await()
        val data = mutableListOf<Routine>()

        for (document in result.documents) {
            val exercises = if (document.get("exercises").toString() == "null") {
                "[]"
            } else {
                document.get("exercises").toString()
            }

            val routine = Routine(
                document.id,
                document.getString("name") ?: "",
                Gson().fromJson(exercises, object : TypeToken<List<String>>() {}.type)
            )

            data.add(routine)
        }

        data
    }
}

fun deleteSelectedRoutine(context: Activity, selectedRoutines: List<Routine>) {
    runBlocking {
        val collectionReference =
            FirebaseFirestore.getInstance().collection("routines")
            .document(Firebase.auth.uid.toString()).collection("routines")
        
        selectedRoutines.forEach { routine ->
            //println(routine.id)
            collectionReference.document(routine.id).delete().await()
        }
        
        Toast.makeText(
            context,
            context.getString(R.string.routine_create_screen_deleted),
            Toast.LENGTH_SHORT
            ).show()
    }
}