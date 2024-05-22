package com.alberto.wcfproject.ui.home.routines

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alberto.wcfproject.data.model.Routine
import com.alberto.wcfproject.databinding.ItemRoutineBinding
import com.alberto.wcfproject.ui.home.routines.detail.RoutineDetailActivity
import com.alberto.wcfproject.utils.collectExercises

class RoutineAdapter(val context: Context, private var data: List<Routine>) :
    RecyclerView.Adapter<RoutineAdapter.RoutineHolder>() {

    private val selectedRoutines = mutableSetOf<Routine>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineHolder {
        return RoutineHolder(
            ItemRoutineBinding.inflate(
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

    inner class RoutineHolder(private val binding: ItemRoutineBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(itemData: Routine) {
            binding.tvName.text = itemData.name
            binding.root.setOnClickListener {
                val exercises = collectExercises(itemData.exercises)
                val intent = Intent(context, RoutineDetailActivity::class.java).apply {
                    putExtra("routine_name", itemData.name)
                    putParcelableArrayListExtra("routine_exercises", ArrayList(exercises))
                }

                context.startActivity(intent)

            }
            binding.cbSelectRoutine.setOnCheckedChangeListener(null) // Clear previous listeners
            binding.cbSelectRoutine.isChecked = selectedRoutines.contains(itemData)
            binding.cbSelectRoutine.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    selectedRoutines.add(itemData)
                } else {
                    selectedRoutines.remove(itemData)
                }
            }
        }
    }

    fun getSelectedRoutines(): List<Routine> {
        return selectedRoutines.toList()
    }

    fun updateData(newData: List<Routine>) {
        data = newData
        notifyDataSetChanged()
    }
}