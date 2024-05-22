package com.alberto.wcfproject.ui.home.routines.create

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alberto.wcfproject.R
import com.alberto.wcfproject.data.model.SelectExercise
import com.alberto.wcfproject.databinding.ActivitySelectExerciseBinding
import com.alberto.wcfproject.utils.collectExercises
import com.alberto.wcfproject.utils.filterSelectedExercises

class RoutineSelectExerciseActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectExerciseBinding
    private lateinit var data: MutableList<SelectExercise>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpViews()

        data = collectExercises().map { SelectExercise(it.uid, it.name, it.image, it.muscleGroup, false) }.toMutableList()

        updateExercisesView(data)
        setupCheckBoxListeners()
    }

    private fun setUpViews() {
        binding.inToolbar.tvTitle.text = getString(R.string.routine_create_screen_choose_exercises)
        binding.inToolbar.ivBack.setOnClickListener {
            onBackPressed()
        }
        binding.fbSelect.setOnClickListener {
            val bundle = Intent().apply {
                putExtra(
                    "selected_exercises",
                    (binding.rvExercices.adapter as SelectExerciseAdapter).dataSelected.toTypedArray()
                )
            }

            setResult(Activity.RESULT_OK, bundle)
            finish()
        }
    }

    // Actualiza la vista del RecyclerView con los ejercicios dados
    private fun updateExercisesView(data: List<SelectExercise>) {
        if (binding.rvExercices.adapter != null) {
            (binding.rvExercices.adapter as SelectExerciseAdapter).data = filterSelectedExercises(
                data,
                binding.chAbdomen.isChecked,
                binding.chBack.isChecked,
                binding.chBiceps.isChecked,
                binding.chChest.isChecked,
                binding.chLegs.isChecked,
                binding.chTriceps.isChecked,
                binding.chShoulders.isChecked
            )
            (binding.rvExercices.adapter as SelectExerciseAdapter).notifyDataSetChanged()
        } else {
            val adapter = SelectExerciseAdapter(data)

            adapter.dataSelected.addAll(
                intent.getParcelableArrayExtra(
                    "selected_exercises",
                    SelectExercise::class.java
                )?.toList() ?: listOf()
            )
            binding.rvExercices.adapter = adapter
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
                updateExercisesView(data)
            }
        }
    }
}