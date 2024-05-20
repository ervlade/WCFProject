package com.alberto.wcfproject.ui.home.exercise

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alberto.wcfproject.R
import com.alberto.wcfproject.data.ExerciseUser
import com.alberto.wcfproject.data.WCFDatabase
import com.alberto.wcfproject.databinding.ActivityExerciseBinding
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.storage.storage

class ExerciseDetailActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityExerciseBinding
    private lateinit var exerciseUser: ExerciseUser
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

        exerciseUser = WCFDatabase.instance!!.exerciseUserDao().getExerciseUserByUid(exerciseUid) ?: ExerciseUser(exerciseUid, null, null, null)

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

                saveExerciseUserData(
                    exerciseUser.uid,
                    binding.etExerciseWeight.text.toString().toFloatOrNull(),
                    binding.etExerciseRepetitions.text.toString().toIntOrNull(),
                    binding.etNotes.text?.toString()
                )

                binding.btUpdate.text = getString(R.string.exercise_detail_screen_edit_save)
                it.tag = "viewMode"
            }
        }
        
        binding.etExerciseWeight.setText(exerciseUser.weight?.toString())
        binding.etExerciseRepetitions.setText(exerciseUser.repetitions?.toString())
        binding.etNotes.setText(exerciseUser.notes)
    }

    private fun saveExerciseUserData(uid: String, weight: Float?, repetitions: Int?, notes: String?) {
        val existingExerciseUser = WCFDatabase.instance!!.exerciseUserDao().getExerciseUserByUid(uid)

        if(existingExerciseUser != null) {
            val updatedExerciseUser = existingExerciseUser.copy(weight = weight, repetitions = repetitions, notes = notes)
            WCFDatabase.instance!!.exerciseUserDao().updateExerciseUser(updatedExerciseUser)
        } else {
            val newExerciseUser = ExerciseUser(uid = uid, weight = weight, repetitions = repetitions, notes = notes)
            WCFDatabase.instance!!.exerciseUserDao().insertExerciseUser(newExerciseUser)
        }
    }
}
