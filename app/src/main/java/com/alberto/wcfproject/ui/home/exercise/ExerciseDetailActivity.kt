package com.alberto.wcfproject.ui.home.exercise

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alberto.wcfproject.R
import com.alberto.wcfproject.data.ExerciseUser
import com.alberto.wcfproject.databinding.ActivityExerciseBinding
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
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

        collectExerciseDataByUserFromFirestore(Firebase.auth.currentUser?.uid ?: "")

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

                binding.btUpdate.text = getString(R.string.exercise_detail_screen_edit_save)

                saveExerciseDataByUserInFirestore(Firebase.auth.currentUser?.uid ?: "")

                it.tag = "viewMode"
            }
        }
    }

    private fun collectExerciseDataByUserFromFirestore(userId: String) {
        FirebaseFirestore.getInstance().collection("exercises_user").document(userId).collection("exercises").document(exerciseUid).get().addOnCompleteListener {
            if(it.isSuccessful) {
                val weight = it.result?.getDouble("weight")?.toFloat()
                val repetitions = it.result?.getLong("repetitions")?.toInt()
                val notes = it.result?.getString("notes")

                if(weight != null || repetitions != null || notes != null) {
                    exerciseUser = ExerciseUser(
                        uid = it.result.id,
                        weight = it.result?.getDouble("weight")?.toFloat(),
                        repetitions = it.result?.getLong("repetitions")?.toInt(),
                        notes = it.result?.getString("notes")
                    )

                    binding.etExerciseWeight.setText(exerciseUser?.weight?.toString())
                    binding.etExerciseRepetitions.setText(exerciseUser?.repetitions?.toString())
                    binding.etNotes.setText(exerciseUser?.notes)
                }
            }
        }
    }

    private fun saveExerciseDataByUserInFirestore(userId: String) {
        val exerciseUserDataToSave = ExerciseUser(
            uid = exerciseUid,
            weight = binding.etExerciseWeight.text.toString().toFloatOrNull(),
            repetitions = binding.etExerciseRepetitions.text.toString().toIntOrNull(),
            notes = binding.etNotes.text?.toString()
        )

        exerciseUser?.let {
            FirebaseFirestore.getInstance().collection("exercises_user").document(userId).collection("exercises").document(exerciseUid).update(exerciseUserDataToSave.toMap())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Datos guardados exitosamente", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(
                            this,
                            "Error al guardar en la base de datos",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        } ?: run {
            FirebaseFirestore.getInstance().collection("exercises_user").document(userId).collection("exercises").document(exerciseUid).set(exerciseUserDataToSave.toMap())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Datos guardados exitosamente", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(
                            this,
                            "Error al guardar en la base de datos",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
}
