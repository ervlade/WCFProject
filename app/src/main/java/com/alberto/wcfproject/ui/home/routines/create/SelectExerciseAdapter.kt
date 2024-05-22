package com.alberto.wcfproject.ui.home.routines.create

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alberto.wcfproject.data.model.SelectExercise
import com.alberto.wcfproject.databinding.ItemSelectExerciseBinding
import com.alberto.wcfproject.utils.getFirebaseImageReference
import com.bumptech.glide.Glide

class SelectExerciseAdapter(var data: List<SelectExercise>) :
    RecyclerView.Adapter<SelectExerciseAdapter.ExerciseHolder>() {

    val dataSelected = mutableListOf<SelectExercise>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseHolder {
        return ExerciseHolder(
            ItemSelectExerciseBinding.inflate(
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

    inner class ExerciseHolder(private val binding: ItemSelectExerciseBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(itemData: SelectExercise) {
            Glide.with(binding.ivExercise)
                .load(getFirebaseImageReference(itemData.image))
                .into(binding.ivExercise)
            binding.tvName.text = itemData.name

            binding.cbSelectExercise.isChecked = dataSelected.contains(itemData)
            binding.cbSelectExercise.setOnClickListener {
                if (binding.cbSelectExercise.isChecked) {
                    dataSelected.add(itemData)
                } else {
                    dataSelected.remove(itemData)
                }
            }
        }
    }
}