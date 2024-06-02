package com.alberto.wcfproject.utils

import android.content.Context
import android.widget.Toast

//Muestra Toast en pantalla
object ToastUtil {
    fun showToast(context: Context, message: String) {
        Toast.makeText(
            context,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }
}
