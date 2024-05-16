package com.alberto.wcfproject.ui.home.exercise

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alberto.wcfproject.R
import com.alberto.wcfproject.data.Exercise
import com.google.firebase.firestore.FirebaseFirestore

class ExercisesFragment : Fragment() {

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exercises, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        collectExercises()


    }

    private fun collectExercises() {
        db = FirebaseFirestore.getInstance()
        val collectionReference = db.collection("exercises")
        collectionReference.get().addOnSuccessListener { querySnapshot ->
            val exerciseList = mutableListOf<Exercise>()
            for (document in querySnapshot.documents) {
                val exercise = Exercise(
                    document.id,
                    document.getString("name") ?: "",
                    document.getString("imageUrl") ?: "",
                    document.getString("muscleGroup") ?: ""

                )
                exerciseList.add(exercise)


            }
            val chestExercise = exerciseList.filter { it.muscleGroup == "chest" }
            val backExercise = exerciseList.filter { it.muscleGroup == "back" }
            val abdomenExercise = exerciseList.filter { it.muscleGroup == "abdomen" }
            val tricepsExercise = exerciseList.filter { it.muscleGroup == "triceps" }
            val bicepsExercise = exerciseList.filter { it.muscleGroup == "biceps" }
            val legsExercise = exerciseList.filter { it.muscleGroup == "legs" }
            val shouldersExercise = exerciseList.filter { it.muscleGroup == "shoulders" }

        }
    }
}