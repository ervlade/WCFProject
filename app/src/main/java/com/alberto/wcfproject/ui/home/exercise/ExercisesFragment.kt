package com.alberto.wcfproject.ui.home.exercise

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alberto.wcfproject.data.Exercise
import com.alberto.wcfproject.databinding.FragmentExercisesBinding
import com.google.firebase.firestore.FirebaseFirestore

class ExercisesFragment : Fragment() {

    private lateinit var binding: FragmentExercisesBinding
    private lateinit var db: FirebaseFirestore

    private lateinit var data: MutableList<Exercise>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentExercisesBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        collectExercises()

        binding.chAbdomen.setOnCheckedChangeListener { buttonView, isChecked ->
            updateExercisesView(filterExercises())
        }
        binding.chBack.setOnCheckedChangeListener { buttonView, isChecked ->
            updateExercisesView(filterExercises())
        }
        binding.chBiceps.setOnCheckedChangeListener { buttonView, isChecked ->
            updateExercisesView(filterExercises())
        }
        binding.chChest.setOnCheckedChangeListener { buttonView, isChecked ->
            updateExercisesView(filterExercises())
        }
        binding.chLegs.setOnCheckedChangeListener { buttonView, isChecked ->
            updateExercisesView(filterExercises())
        }
        binding.chTriceps.setOnCheckedChangeListener { buttonView, isChecked ->
            updateExercisesView(filterExercises())
        }
        binding.chShoulders.setOnCheckedChangeListener { buttonView, isChecked ->
            updateExercisesView(filterExercises())
        }
    }

    private fun collectExercises() {
        db = FirebaseFirestore.getInstance()
        val collectionReference = db.collection("exercises")

        collectionReference.get().addOnSuccessListener { querySnapshot ->
            data = mutableListOf<Exercise>()

            for (document in querySnapshot.documents) {
                val exercise = Exercise(
                    document.id,
                    document.getString("name") ?: "",
                    document.getString("imageUrl") ?: "",
                    document.getString("muscleGroup") ?: ""
                )

                data.add(exercise)
            }

            data.sortWith(compareBy({it.muscleGroup}, {it.name}))
            updateExercisesView(data)
        }
    }

    private fun filterExercises(): List<Exercise> {
        val filteredData = mutableListOf<Exercise>()

        if(
            !binding.chAbdomen.isChecked &&
            !binding.chBack.isChecked &&
            !binding.chBiceps.isChecked &&
            !binding.chChest.isChecked &&
            !binding.chLegs.isChecked &&
            !binding.chTriceps.isChecked &&
            !binding.chShoulders.isChecked
        ) {
            filteredData += data
        } else {
            if(binding.chAbdomen.isChecked) {
                filteredData += data.filter { it.muscleGroup == "abdomen" }
            }

            if(binding.chBack.isChecked) {
                filteredData += data.filter { it.muscleGroup == "back" }
            }

            if(binding.chBiceps.isChecked) {
                filteredData += data.filter { it.muscleGroup == "biceps" }
            }

            if(binding.chChest.isChecked) {
                filteredData += data.filter { it.muscleGroup == "chest" }
            }

            if(binding.chLegs.isChecked) {
                filteredData += data.filter { it.muscleGroup == "legs" }
            }

            if(binding.chTriceps.isChecked) {
                filteredData += data.filter { it.muscleGroup == "triceps" }
            }

            if(binding.chShoulders.isChecked) {
                filteredData += data.filter { it.muscleGroup == "shoulders" }
            }
        }

        return filteredData
    }

    private fun updateExercisesView(data: List<Exercise>) {
        if(binding.rvExercices.adapter != null) {
            (binding.rvExercices.adapter as ExerciseAdapter).data = filterExercises()
            (binding.rvExercices.adapter as ExerciseAdapter).notifyDataSetChanged()
        } else {
            binding.rvExercices.adapter = ExerciseAdapter(data)
        }
    }
}