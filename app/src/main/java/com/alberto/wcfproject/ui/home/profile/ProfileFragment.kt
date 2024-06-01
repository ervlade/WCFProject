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
                binding.etWeight.setText("${user.weight} kg")
            }

            if (user.height > 0f) {
                binding.etHeight.setText("${user.height} cm")
            }

            if (user.weight > 0f && user.height > 0f) {
                binding.etImc.setText(
                    String.format(
                        "%.2f IMC",
                        calculateIMC(user.weight, user.height)
                    )
                )
            }
        }

        binding.btEditSave.setOnClickListener {
            if (it.tag == "viewMode") {
                binding.etWeight.isEnabled = true
                binding.etHeight.isEnabled = true

                binding.btEditSave.text = getString(R.string.profile_screen_save_data)
                it.tag = "editMode"
            } else {
                // Obtener los valores sin unidades para el cálculo
                val weight = binding.etWeight.text.toString().replace(" kg", "").toFloat()
                val height = binding.etHeight.text.toString().replace(" cm", "").toInt()

                binding.etWeight.isEnabled = false
                binding.etHeight.isEnabled = false

                // Calcular y mostrar el nuevo IMC con la unidad "IMC"
                binding.etImc.setText(String.format("%.2f IMC", calculateIMC(weight, height)))

                // Guardar los datos del usuario
                saveUserData(
                    requireActivity(),
                    user?.copy(weight = weight, height = height)
                )

                // Actualizar la vista con las unidades correctas
                binding.etWeight.setText("$weight kg")
                binding.etHeight.setText("$height cm")

                binding.btEditSave.text = getString(R.string.common_edit_save)
                it.tag = "viewMode"
            }
        }

        binding.btSignOff.setOnClickListener {
            LogoutDialog().show(parentFragmentManager, "DialogFragment")
        }
    }

}