package com.alberto.wcfproject.ui.home.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alberto.wcfproject.data.WCFDatabase
import com.alberto.wcfproject.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private val user = WCFDatabase.instance?.userDao()?.activeUser()
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        collectUserData()
        setUpViews()
    }

    private fun setUpViews() {
        binding.btEditSave.setOnClickListener {
            if (it.tag == "viewMode") {
                binding.etWeight.isEnabled = true
                binding.etHeight.isEnabled = true

                binding.btEditSave.text = "Guardar datos"
                it.tag = "editMode"
            } else {
                binding.etWeight.isEnabled = false
                binding.etHeight.isEnabled = false

                binding.etImc.setText(
                    String.format(
                        "%.2f",
                        calculateIMC(
                            binding.etWeight.text.toString().toFloat(),
                            binding.etHeight.text.toString().toInt()
                        )
                    )
                )
                binding.btEditSave.text = "Editar datos"

                saveUserData(binding.etWeight.text.toString().toFloat(), binding.etHeight.text.toString().toInt())
                it.tag = "viewMode"
            }
        }
    }

    private fun collectUserData() {
        if(user != null) {
            binding.etEmail.setText(user.email)

            if(user.weight > 0f) {
                binding.etWeight.setText(user.weight.toString())
            }

            if(user.height > 0f) {
                binding.etHeight.setText(user.height.toString())
            }

            if(user.weight > 0f && user.height > 0f) {
                binding.etImc.setText(String.format("%.2f", calculateIMC(user.weight, user.height)))
            }
        }
    }

    private fun saveUserData(weight: Float, height: Int) {
        val userCopy = user?.copy(weight = weight, height = height)

        if(userCopy != null) {
            WCFDatabase.instance?.userDao()?.updateActiveUser(userCopy)
        }
    }

    private fun calculateIMC(weight: Float, height: Int): Float {
        return weight / (height * 0.02f)
    }
}