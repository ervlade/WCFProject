package com.alberto.wcfproject.ui.home.exercise

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alberto.wcfproject.data.Exercise
import com.alberto.wcfproject.databinding.ItemExerciseBinding
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.storage.storage

class ExerciseAdapter(var data: List<Exercise>) :
    RecyclerView.Adapter<ExerciseAdapter.ExerciseHolder>() {
        
    val storageRef = Firebase.storage.reference

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
            Glide.with(binding.ivExercise).load(storageRef.child(itemData.image))
                .into(binding.ivExercise)
            binding.tvName.text = itemData.name
        }
    }
}