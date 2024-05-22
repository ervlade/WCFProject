package com.alberto.wcfproject.ui.home.routines

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alberto.wcfproject.databinding.FragmentRoutinesBinding
import com.alberto.wcfproject.ui.home.routines.create.RoutineCreateActivity
import com.alberto.wcfproject.utils.collectRoutines
import com.alberto.wcfproject.utils.deleteSelectedRoutine

class RoutinesFragment : Fragment() {

    private lateinit var binding: FragmentRoutinesBinding
    private lateinit var adapter: RoutineAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentRoutinesBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViews()
    }

    override fun onResume() {
        super.onResume()

        adapter.updateData(collectRoutines())

        //binding.rvRoutines.adapter = RoutineAdapter(requireContext(), collectRoutines())
    }

    private fun setUpViews() {
        binding.fbCreate.setOnClickListener {
            startActivity(Intent(activity, RoutineCreateActivity::class.java))
        }
        binding.fbEliminate.setOnClickListener {
            deleteSelectedRoutine(requireActivity(), adapter.getSelectedRoutines())

            adapter.updateData(collectRoutines())

            //binding.rvRoutines.adapter = RoutineAdapter(requireContext(), collectRoutines())
            //binding.rvRoutines.adapter = adapter
        }

        adapter = RoutineAdapter(requireContext(), collectRoutines())
        binding.rvRoutines.adapter = adapter
    }
}