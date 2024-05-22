package com.alberto.wcfproject.ui.home.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alberto.wcfproject.R
import com.alberto.wcfproject.data.database.WCFDatabase
import com.alberto.wcfproject.databinding.FragmentProfileBinding
import com.alberto.wcfproject.utils.calculateIMC
import com.alberto.wcfproject.utils.saveUserData

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

        setUpViews()
    }

    // Configura las vistas y los listeners de botones
    private fun setUpViews() {
        if (user != null) {
            binding.etEmail.setText(user.email)

            if (user.weight > 0f) {
                binding.etWeight.setText(user.weight.toString())
            }

            if (user.height > 0f) {
                binding.etHeight.setText(user.height.toString())
            }

            if (user.weight > 0f && user.height > 0f) {
                binding.etImc.setText(String.format("%.2f", calculateIMC(user.weight, user.height)))
            }
        }

        binding.btEditSave.setOnClickListener {
            if (it.tag == "viewMode") {
                binding.etWeight.isEnabled = true
                binding.etHeight.isEnabled = true

                binding.btEditSave.text = getString(R.string.profile_screen_save_data)
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
                binding.btEditSave.text = getString(R.string.common_edit_save)

                saveUserData(
                    requireActivity(),
                    user?.copy(weight = binding.etWeight.text.toString().toFloat(), height = binding.etHeight.text.toString().toInt())
                )
                it.tag = "viewMode"
            }

        }

        binding.btSignOff.setOnClickListener {
            LogoutDialog().show(parentFragmentManager, "DialogFragment")
        }
    }
}