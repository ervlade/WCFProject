package com.alberto.wcfproject.ui.home.profile

// LogoutConfirmationDialogFragment.kt

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.appcompat.app.AlertDialog
import com.alberto.wcfproject.databinding.FragmentDialogBinding
import com.alberto.wcfproject.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class DialogFragment : DialogFragment() {

    private lateinit var binding: FragmentDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentDialogBinding.inflate(layoutInflater)

        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .create().apply {
                binding.btDialogAccept.setOnClickListener {
                    logout()
                }
                binding.btDialogCancel.setOnClickListener {
                    dismiss()

                }
            }
    }

    //Cierra sesión y vuelve al login
    private fun logout() {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(activity, LoginActivity::class.java)
        Toast.makeText(activity, "Sesión cerrada", Toast.LENGTH_SHORT).show()
        startActivity(intent)
    }

}
