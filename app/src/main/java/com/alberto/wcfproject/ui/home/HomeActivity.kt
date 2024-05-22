package com.alberto.wcfproject.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import com.alberto.wcfproject.R
import com.alberto.wcfproject.databinding.ActivityHomeBinding
import com.alberto.wcfproject.ui.home.exercise.ExercisesFragment
import com.alberto.wcfproject.ui.home.profile.ProfileFragment
import com.alberto.wcfproject.ui.home.routines.RoutinesFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setUpViews()
    }

    // Configura las vistas y el comportamiento del menú
    private fun setUpViews() {
        navigateFragment(getString(R.string.common_exercises), ExercisesFragment())

        binding.inToolbar.ivBack.setImageResource(R.drawable.menu)
        binding.inToolbar.ivBack.setOnClickListener {
            binding.dlMenu.openDrawer(GravityCompat.START)
        }
        binding.nvMenu.menu.forEach { menuItem ->
            menuItem.setOnMenuItemClickListener { menu ->
                when (menu.itemId) {
                    R.id.mn_exercises -> {
                        navigateFragment(
                            getString(R.string.common_exercises),
                            ExercisesFragment()
                        )
                    }

                    R.id.mn_routines -> {
                        navigateFragment(
                            getString(R.string.routines_screen_title),
                            RoutinesFragment()
                        )
                    }

                    R.id.mn_profile -> {
                        navigateFragment(
                            getString(R.string.profile_screen_title),
                            ProfileFragment()
                        )
                    }
                }

                binding.dlMenu.closeDrawer(GravityCompat.START)
                true
            }
        }
    }

    // Controla el comportamiento del botón de retroceso para cerrar la aplicación
    override fun onBackPressed() {
        finishAffinity()

        super.onBackPressed()
    }

    // Navega a un fragmento específico y actualiza el título en la barra de herramientas
    private fun navigateFragment(title: String, fragment: Fragment) {
        binding.inToolbar.tvTitle.text = title
        supportFragmentManager.beginTransaction().replace(R.id.fc_home, fragment).commit()
    }
}