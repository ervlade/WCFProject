package com.alberto.wcfproject.ui.home.profile

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.alberto.wcfproject.R
import com.alberto.wcfproject.data.database.WCFDatabase
import com.alberto.wcfproject.databinding.FragmentDialogBinding
import com.alberto.wcfproject.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class LogoutDialog : DialogFragment() {

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
        WCFDatabase.instance?.clearAllTables()

        Toast.makeText(
            activity,
            getString(R.string.dialog_screen_session_close),
            Toast.LENGTH_SHORT
        ).show()
        startActivity(Intent(activity, LoginActivity::class.java))
    }
}
