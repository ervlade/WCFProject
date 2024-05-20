package com.alberto.wcfproject.ui.home.routines

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alberto.wcfproject.databinding.FragmentRoutinesBinding
import com.alberto.wcfproject.ui.home.routines.create.RoutineCreateActivity

class RoutinesFragment : Fragment() {

    private lateinit var binding: FragmentRoutinesBinding

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

    private fun setUpViews() {
        binding.fbCreate.setOnClickListener {
            startActivity(Intent(activity, RoutineCreateActivity::class.java))
        }
    }
}