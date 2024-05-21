package com.alberto.wcfproject.ui.home.routines

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alberto.wcfproject.data.Exercise
import com.alberto.wcfproject.data.Routine
import com.alberto.wcfproject.databinding.ItemExerciseRoutineBinding
import com.alberto.wcfproject.ui.home.routines.detail.RoutineDetailActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class RoutineAdapter(val context: Context, private val data: List<Routine>) :
    RecyclerView.Adapter<RoutineAdapter.RoutineHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineHolder {
        return RoutineHolder(
            ItemExerciseRoutineBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RoutineHolder, position: Int) {
        holder.bind(data[position])

    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class RoutineHolder(private val binding: ItemExerciseRoutineBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(itemData: Routine) {
            binding.tvName.text = itemData.name
            binding.root.setOnClickListener {
                val exercises = collectExercisesFirebase(itemData.exercises)
                val intent = Intent(context, RoutineDetailActivity::class.java).apply {
                    putExtra("routine_name", itemData.name)
                    putParcelableArrayListExtra("routine_exercises", ArrayList(exercises))
                }

                context.startActivity(intent)
            }
        }

        private fun collectExercisesFirebase(exercisesIds: List<String>): List<Exercise> {
            val db = FirebaseFirestore.getInstance()
            val data = mutableListOf<Exercise>()

            return runBlocking {
                exercisesIds.forEach {
                    val result = db.collection("exercises").document(it).get().await()

                    val exercise = Exercise(
                        result.id,
                        result.getString("name") ?: "",
                        result.getString("imageUrl") ?: "",
                        result.getString("muscleGroup") ?: ""
                    )

                    data.add(exercise)
                }

                data
            }
        }
    }
}