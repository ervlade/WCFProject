package com.alberto.wcfproject.ui.register

import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alberto.wcfproject.R
import com.alberto.wcfproject.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setUpViews()
    }

    private fun setUpViews() {
        binding.inToolbar.tvTitle.text = getString(R.string.register_screen_title)
        binding.inToolbar.ivBack.setOnClickListener {
            onBackPressed()
        }

        binding.btRegister.setOnClickListener {
            if (validateFields()) {
                registerUser(binding.etEmail.text.toString(), binding.etPassword.text.toString())
            } else {
                Toast.makeText(this, getString(R.string.common_error_fields), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
    //Crea un nuevo usuario en Firebase Authentication
    private fun registerUser(email: String, password: String) {
        val auth: FirebaseAuth = FirebaseAuth.getInstance()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    finish()
                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.register_screen_error_firebase),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
    //Valida los campos de registro del usuario
    private fun validateFields(): Boolean {
        var valid = true

        if (!Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text).matches()) {
            valid = false
        }

        if (valid && binding.etPassword.text.toString() != binding.etRepeatPassword.text.toString()) {
            valid = false
        }

        if (valid && binding.etWeight.text.isEmpty()) {
            valid = false
        }

        if (valid && binding.etHeight.text.isEmpty()) {
            valid = false
        }

        return valid
    }
}