package com.alberto.wcfproject.ui.home.exercise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alberto.wcfproject.data.model.Exercise
import com.alberto.wcfproject.databinding.FragmentExercisesBinding
import com.alberto.wcfproject.utils.collectExercises
import com.alberto.wcfproject.utils.filterExercises

class ExercisesFragment : Fragment() {

    private lateinit var binding: FragmentExercisesBinding

    private lateinit var data: List<Exercise>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentExercisesBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        data = collectExercises()

        updateExercisesView(data)
        setupCheckBoxListeners()
    }

    // Actualiza la vista del RecyclerView con los ejercicios dados
    private fun updateExercisesView(data: List<Exercise>) {
        if (binding.rvExercices.adapter != null) {
            (binding.rvExercices.adapter as ExerciseAdapter).data = filterExercises(
                data,
                binding.chAbdomen.isChecked,
                binding.chBack.isChecked,
                binding.chBiceps.isChecked,
                binding.chChest.isChecked,
                binding.chLegs.isChecked,
                binding.chTriceps.isChecked,
                binding.chShoulders.isChecked
                )
            (binding.rvExercices.adapter as ExerciseAdapter).notifyDataSetChanged()
        } else {
            binding.rvExercices.adapter = ExerciseAdapter(data)
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