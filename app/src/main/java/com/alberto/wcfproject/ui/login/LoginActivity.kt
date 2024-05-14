package com.alberto.wcfproject.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alberto.wcfproject.R
import com.alberto.wcfproject.data.User
import com.alberto.wcfproject.data.WCFDatabase
import com.alberto.wcfproject.databinding.ActivityLoginBinding
import com.alberto.wcfproject.ui.home.HomeActivity
import com.alberto.wcfproject.ui.register.RegisterActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setUpViews()
    }

    private fun setUpViews() {
        binding.btLogin.setOnClickListener {

            if (validateFields()) {
                loginUser(binding.etEmail.text.toString(), binding.etPassword.text.toString())
            } else {
                Toast.makeText(this, getString(R.string.common_error_fields), Toast.LENGTH_SHORT)
                    .show()
            }
        }
        binding.btRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun validateFields(): Boolean {
        var valid = true

        if (!Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text).matches()) {
            valid = false
        }

        if (valid && binding.etPassword.text.isEmpty()) {
            valid = false
        }

        return valid
    }

    private fun loginUser(email: String, password: String) {

        val auth: FirebaseAuth = FirebaseAuth.getInstance()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Inicio de sesión exitoso

                    WCFDatabase.instance?.userDao()?.insertAll(User(auth.currentUser?.uid ?: "", email, 0f, 0))

                    val intent = Intent(this, HomeActivity::class.java)

                    startActivity(intent)
                } else {
                    // El inicio de sesión falló

                    Toast.makeText(this, getString(R.string.login_screen_error), Toast.LENGTH_SHORT).show()
                }

            }
    }
}