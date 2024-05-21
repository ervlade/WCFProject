package com.alberto.wcfproject.ui.home.routines.create

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alberto.wcfproject.data.SelectExercise
import com.alberto.wcfproject.databinding.ItemExerciseRoutineBinding

class ExerciseRoutineAdapter(var data: List<SelectExercise>) :
    RecyclerView.Adapter<ExerciseRoutineAdapter.ExerciseHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseHolder {
        return ExerciseHolder(
            ItemExerciseRoutineBinding.inflate(
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

    inner class ExerciseHolder(private val binding: ItemExerciseRoutineBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(itemData: SelectExercise) {
            binding.tvName.text = itemData.name


        }
    }

}