package com.alberto.wcfproject.ui.home.routines.create

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.alberto.wcfproject.data.SelectExercise
import com.alberto.wcfproject.databinding.ActivityRoutineCreateBinding

class RoutineCreateActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityRoutineCreateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoutineCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        val a = ActivityResultContracts.GetContent()
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            
        }
        setUpViews()
    }

    private fun setUpViews() {
        binding.inToolbar.tvTitle.text = "Crear rutina"
        binding.inToolbar.ivBack.setOnClickListener {
            onBackPressed()
        }
        

        binding.btAddExercises.setOnClickListener {
            startActivityForResult(Intent(this, RoutineSelectExerciseActivity::class.java), 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        
        if(resultCode == Activity.RESULT_OK && requestCode == 1) {
            val exercisesSelected = data?.getParcelableArrayExtra("selected_exercises", SelectExercise::class.java)
            
            exercisesSelected?.forEach {
                println(it)
            }
        }
    }
}
