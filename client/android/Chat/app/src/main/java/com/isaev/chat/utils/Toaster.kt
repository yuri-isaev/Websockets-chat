package com.isaev.chat.utils

import android.app.AlertDialog
import android.content.Context
import com.isaev.chat.R

object Toaster {
  fun showAlert(
    context: Context,
    title: String = "",
    message: String = "",
    onYes: Runnable? = null,
    onNo: Runnable? = null
  ) {
    val alertDialog = AlertDialog.Builder(context)
    alertDialog.setTitle(title)
    alertDialog.setMessage(message)
    alertDialog.setPositiveButton(R.string.dialog_yes) { _, _ -> onYes?.run() }
    alertDialog.setNegativeButton(R.string.dialog_no) { _, _ -> onNo?.run() }
    alertDialog.show()
  }
}

// Wrapper class
class ToasterWrapper {
  fun showAlert(context: Context, title: String, message: String) {
    Toaster.showAlert(context, title, message)
  }
}