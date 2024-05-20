package com.alberto.wcfproject.ui.home.routines.create

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alberto.wcfproject.data.SelectExercise
import com.alberto.wcfproject.databinding.ActivitySelectExerciseBinding
import com.google.firebase.firestore.FirebaseFirestore

class RoutineSelectExerciseActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectExerciseBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var data: MutableList<SelectExercise>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpViews()
        collectExercises()
        setupCheckBoxListeners()
    }

    private fun setUpViews() {
        binding.inToolbar.tvTitle.text = "Elegir ejercicios"
        binding.inToolbar.ivBack.setOnClickListener {
            onBackPressed()
        }
        binding.fbSelect.setOnClickListener {
             val bundle = Intent().apply {
                 putExtra("selected_exercises", (binding.rvExercices.adapter as SelectExerciseAdapter).dataSelected.toTypedArray())
            }
            
            setResult(Activity.RESULT_OK, bundle)
            finish()
        }
    }
    private fun collectExercises() {
        db = FirebaseFirestore.getInstance()
        val collectionReference = db.collection("exercises")

        collectionReference.get().addOnSuccessListener { querySnapshot ->
            data = mutableListOf()

            for (document in querySnapshot.documents) {
                val exercise = SelectExercise(
                    document.id,
                    document.getString("name") ?: "",
                    document.getString("imageUrl") ?: "",
                    document.getString("muscleGroup") ?: "",
                    false
                )

                data.add(exercise)
            }

            data.sortWith(compareBy({it.muscleGroup}, {it.name}))
            updateExercisesView(data)
        }
    }

    // Filtra los ejercicios según los CheckBox seleccionados
    private fun filterExercises(): List<SelectExercise> {
        val filteredData = mutableListOf<SelectExercise>()

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

    // Actualiza la vista del RecyclerView con los ejercicios dados
    private fun updateExercisesView(data: List<SelectExercise>) {
        if(binding.rvExercices.adapter != null) {
            (binding.rvExercices.adapter as SelectExerciseAdapter).data = filterExercises()
            (binding.rvExercices.adapter as SelectExerciseAdapter).notifyDataSetChanged()
        } else {
            binding.rvExercices.adapter = SelectExerciseAdapter(data)
        }
    }
    private fun setupCheckBoxListeners() {
        val checkBoxes = listOf(
            binding.chAbdomen,
            binding.chBack,
            binding.chBiceps,
            binding.chChest,
            binding.chLegs,
            binding.chTriceps,
            binding.chShoulders
        )

        checkBoxes.forEach { checkBox ->
            checkBox.setOnCheckedChangeListener { _, _ ->
                updateExercisesView(filterExercises())
            }
        }
    }
}