package com.alberto.wcfproject.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alberto.wcfproject.R
import com.alberto.wcfproject.data.database.WCFDatabase
import com.alberto.wcfproject.data.model.User
import com.alberto.wcfproject.databinding.ActivityLoginBinding
import com.alberto.wcfproject.ui.home.HomeActivity
import com.alberto.wcfproject.ui.register.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var userData: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setUpViews()
    }

    // Configura las vistas y los listeners de botones
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

    //Valida los campos de entrada del usuario
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

    //Iniciar sesión en Firebase Authentication
    private fun loginUser(email: String, password: String) {
        val auth: FirebaseAuth = FirebaseAuth.getInstance()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val firebaseUser = auth.currentUser
                    if (firebaseUser != null && firebaseUser.isEmailVerified) {
                        collectUserData(firebaseUser.uid)
                    } else {
                        auth.signOut()
                        Toast.makeText(this, getString(R.string.login_screen_email_not_verified), Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    Toast.makeText(this, getString(R.string.login_screen_error), Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }

    // Recolecta los datos de usuario de la base de datos Firestore
    private fun collectUserData(userId: String) {
        db = FirebaseFirestore.getInstance()
        val collectionReference = db.collection("users").document(userId)

        collectionReference.get().addOnCompleteListener { querySnapshot ->
            if (querySnapshot.isSuccessful) {
                userData = User(
                    userId,
                    querySnapshot.result?.getString("email") ?: "",
                    querySnapshot.result?.getDouble("weight")?.toFloat() ?: 0f,
                    querySnapshot.result?.getLong("height")?.toInt() ?: 0
                )

                WCFDatabase.instance?.userDao()
                    ?.insertActiveUser(userData)

                val intent = Intent(this, HomeActivity::class.java)

                startActivity(intent)
            } else {

                Toast.makeText(this, getString(R.string.login_screen_error), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}