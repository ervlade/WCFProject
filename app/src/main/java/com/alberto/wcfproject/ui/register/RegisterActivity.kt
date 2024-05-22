package com.alberto.wcfproject.ui.register

import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alberto.wcfproject.R
import com.alberto.wcfproject.data.model.User
import com.alberto.wcfproject.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializa Firebase Firestore y Auth
        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        setUpViews()
    }

    // Configura las vistas y los listeners de botones
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

    // Crea un nuevo usuario en Firebase Authentication
    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // El usuario se registró exitosamente
                    val firebaseUser = auth.currentUser
                    if (firebaseUser != null) {
                        // Crear el objeto UserData con los datos ingresados por el usuario
                        val user = User(
                            firebaseUser.uid,
                            email,
                            binding.etWeight.text.toString().toFloat(),
                            binding.etHeight.text.toString().toInt()
                        )
                        createUserInFirestore(user)
                    }
                } else {
                    // Error al registrar el usuario
                    Toast.makeText(
                        this,
                        getString(R.string.register_screen_error_firebase),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    // Crea el usuario en Firestore
    private fun createUserInFirestore(user: User) {
        firestore.collection("users").document(user.uid).set(user.toMap())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this, getString(R.string.register_screen_successfully_register),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    finish()
                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.common_register_fail),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    // Valida los campos de registro del usuario
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