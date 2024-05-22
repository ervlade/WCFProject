package com.alberto.wcfproject.ui.home.exercise

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alberto.wcfproject.R
import com.alberto.wcfproject.data.model.ExerciseUser
import com.alberto.wcfproject.databinding.ActivityExerciseBinding
import com.alberto.wcfproject.utils.collectExerciseDataByUserFromFirestore
import com.alberto.wcfproject.utils.saveExerciseDataByUserInFirestore
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.storage.storage

class ExerciseDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExerciseBinding
    private var exerciseUser: ExerciseUser? = null
    private lateinit var exerciseUid: String
    private lateinit var exerciseName: String
    private lateinit var exerciseImage: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        exerciseUid = intent.getStringExtra("exerciseUid") ?: ""
        exerciseName = intent.getStringExtra("exerciseName") ?: ""
        exerciseImage = intent.getStringExtra("exerciseImage") ?: ""

        exerciseUser = collectExerciseDataByUserFromFirestore(Firebase.auth.currentUser?.uid ?: "", exerciseUid)

        setUpViews()
    }

    private fun setUpViews() {
        binding.inToolbar.tvTitle.text = exerciseName
        binding.inToolbar.ivBack.setOnClickListener {
            onBackPressed()
        }

        Glide.with(this)
            .load(Firebase.storage.reference.child(exerciseImage))
            .into(binding.ivExercise)

        binding.btUpdate.setOnClickListener {
            if (it.tag == "viewMode") {
                binding.etExerciseWeight.isEnabled = true
                binding.etExerciseRepetitions.isEnabled = true
                binding.etNotes.isEnabled = true

                binding.btUpdate.text = getString(R.string.exercise_detail_screen_save_data)
                it.tag = "editMode"
            } else {
                binding.etExerciseWeight.isEnabled = false
                binding.etExerciseRepetitions.isEnabled = false
                binding.etNotes.isEnabled = false

                binding.btUpdate.text = getString(R.string.common_edit_save)

                saveExerciseDataByUserInFirestore(this, Firebase.auth.currentUser?.uid ?: "", ExerciseUser(
                    uid = exerciseUid,
                    weight = binding.etExerciseWeight.text.toString().toFloatOrNull(),
                    repetitions = binding.etExerciseRepetitions.text.toString().toIntOrNull(),
                    notes = binding.etNotes.text?.toString()
                    ),
                    exerciseUser == null)

                it.tag = "viewMode"
            }
        }

        binding.etExerciseWeight.setText(exerciseUser?.weight?.toString())
        binding.etExerciseRepetitions.setText(exerciseUser?.repetitions?.toString())
        binding.etNotes.setText(exerciseUser?.notes)
    }
}
