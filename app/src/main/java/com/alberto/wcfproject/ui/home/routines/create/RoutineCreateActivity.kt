package com.alberto.wcfproject.ui.home.routines.create

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alberto.wcfproject.data.Routine
import com.alberto.wcfproject.data.SelectExercise
import com.alberto.wcfproject.databinding.ActivityRoutineCreateBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class RoutineCreateActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityRoutineCreateBinding
    private var dataSelected = listOf<SelectExercise>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoutineCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpViews()
    }

    private fun setUpViews() {
        binding.inToolbar.tvTitle.text = "Crear rutina"
        binding.inToolbar.ivBack.setOnClickListener {
            onBackPressed()
        }
        binding.btUpdate.setOnClickListener {
            saveRoutineDataByUserInFirestore(Firebase.auth.uid ?: "")
        }
        binding.btAddExercises.setOnClickListener {
            val intent = Intent(this, RoutineSelectExerciseActivity::class.java).apply {
                putExtra("selected_exercises", dataSelected.toTypedArray())
            }
            startActivityForResult(intent, 1)
        }
    }

    private fun saveRoutineDataByUserInFirestore(userId: String) {
        val routineData = Routine(
            name = binding.etRoutineName.text.toString(),
            exercises = dataSelected.map { it.uid }
        )

        FirebaseFirestore.getInstance().collection("routines").document(userId).collection("routines").document().set(routineData.toMap())
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        
        if(resultCode == Activity.RESULT_OK && requestCode == 1) {
            dataSelected = data?.getParcelableArrayExtra("selected_exercises", SelectExercise::class.java)?.toList() ?: listOf()


            binding.rvExercises.adapter = ExerciseRoutineAdapter(dataSelected)

        }
    }
}
