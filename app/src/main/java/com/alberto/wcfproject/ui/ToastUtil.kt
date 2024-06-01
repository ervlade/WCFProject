package com.alberto.wcfproject.ui

import android.content.Context
import android.widget.Toast

object ToastUtil {
    fun showToast(context: Context, message: String) {
        Toast.makeText(
            context,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }
}
