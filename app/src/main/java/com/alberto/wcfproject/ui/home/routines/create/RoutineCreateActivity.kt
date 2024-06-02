package com.alberto.wcfproject.ui.home.routines.create

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alberto.wcfproject.R
import com.alberto.wcfproject.data.model.SelectExercise
import com.alberto.wcfproject.databinding.ActivityRoutineCreateBinding
import com.alberto.wcfproject.utils.ToastUtil
import com.alberto.wcfproject.utils.saveRoutineDataByUserInFirestore
import com.google.firebase.auth.ktx.auth
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
        binding.inToolbar.tvTitle.text = getString(R.string.routine_create_screen_save_routine)
        binding.inToolbar.ivBack.setOnClickListener {
            onBackPressed()
        }
        binding.btUpdate.setOnClickListener {
            val routineName = binding.etRoutineName.text.toString()
            if (routineName.isNotEmpty()) {
                saveRoutineDataByUserInFirestore(
                    this,
                    Firebase.auth.uid ?: "",
                    binding.etRoutineName.text.toString(),
                    dataSelected
                )

            } else {
                ToastUtil.showToast(this, getString(R.string.routine_create_screen_name_write))



            }
        }
        binding.btAddExercises.setOnClickListener {
            val intent = Intent(this, RoutineSelectExerciseActivity::class.java).apply {
                putExtra("selected_exercises", dataSelected.toTypedArray())
            }
            startActivityForResult(intent, 1)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            dataSelected =
                data?.getParcelableArrayExtra("selected_exercises", SelectExercise::class.java)
                    ?.toList() ?: listOf()

            binding.rvExercises.adapter = ExerciseRoutineAdapter(dataSelected)
        }
    }
}
