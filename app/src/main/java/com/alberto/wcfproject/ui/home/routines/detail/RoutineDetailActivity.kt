package com.alberto.wcfproject.ui.home.routines.detail

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alberto.wcfproject.data.model.Exercise
import com.alberto.wcfproject.databinding.ActivityDetailRoutineBinding
import com.alberto.wcfproject.ui.home.exercise.ExerciseAdapter

class RoutineDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailRoutineBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailRoutineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpViews()

    }

    private fun setUpViews() {
        binding.inToolbar.tvTitle.text = intent.getStringExtra("routine_name")
        binding.inToolbar.ivBack.setOnClickListener {
            onBackPressed()
        }

        val exercises = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableArrayListExtra("routine_exercises", Exercise::class.java)?.toList()
                ?: listOf()
        } else {
            TODO("VERSION.SDK_INT < TIRAMISU")
        }

        binding.rvExercices.adapter = ExerciseAdapter(exercises)
    }
}