package com.alberto.wcfproject.ui.home.exercise

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alberto.wcfproject.data.model.Exercise
import com.alberto.wcfproject.databinding.ItemExerciseBinding
import com.alberto.wcfproject.utils.getFirebaseImageReference
import com.bumptech.glide.Glide

class ExerciseAdapter(var data: List<Exercise>) :
    RecyclerView.Adapter<ExerciseAdapter.ExerciseHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseHolder {
        return ExerciseHolder(
            ItemExerciseBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ExerciseHolder, position: Int) {
        holder.bind(data[position])

    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ExerciseHolder(private val binding: ItemExerciseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(itemData: Exercise) {
            binding.root.setOnClickListener {
                val position = adapterPosition

                if (position != RecyclerView.NO_POSITION) {
                    val exercise = data[position]
                    val intent =
                        Intent(binding.root.context, ExerciseDetailActivity::class.java).apply {
                            putExtra("exerciseUid", exercise.uid)
                            putExtra("exerciseName", exercise.name)
                            putExtra("exerciseImage", exercise.image)
                        }

                    binding.root.context.startActivity(intent)
                }
            }
            Glide.with(binding.ivExercise)
                .load(getFirebaseImageReference(itemData.image))
                .into(binding.ivExercise)
            binding.tvName.text = itemData.name
        }
    }
}