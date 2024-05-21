package com.alberto.wcfproject.ui.home.routines

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alberto.wcfproject.data.Routine
import com.alberto.wcfproject.databinding.FragmentRoutinesBinding
import com.alberto.wcfproject.ui.home.routines.create.RoutineCreateActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

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
        collectRoutines()
    }

    private fun setUpViews() {
        binding.fbCreate.setOnClickListener {
            startActivity(Intent(activity, RoutineCreateActivity::class.java))
        }
    }

    private fun collectRoutines() {
        val db = FirebaseFirestore.getInstance()
        val collectionReference =
            db.collection("routines").document(Firebase.auth.uid.toString()).collection("routines")

        collectionReference.get().addOnSuccessListener { querySnapshot ->
            val data = mutableListOf<Routine>()

            for (document in querySnapshot.documents) {
                val exercises = if(document.get("exercises").toString() == "null") {
                    "[]"
                } else {
                    document.get("exercises").toString()
                }

                val routine = Routine(
                    document.getString("name") ?: "",
                    Gson().fromJson(exercises, object : TypeToken<List<String>>() {}.type)
                )

                data.add(routine)
            }
            binding.rvRoutines.adapter = RoutineAdapter(requireContext(), data)
        }
    }
}