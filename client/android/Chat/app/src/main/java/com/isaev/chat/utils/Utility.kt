package com.isaev.chat.utils

import android.app.AlertDialog
import android.content.Context
import com.isaev.chat.R

object Utility {

	fun showAlert(
		context: Context,
		title: String = "",
		message: String = "",
		onYes: Runnable? = null,
		onNo: Runnable? = null
	) {
		val alertDialogBuilder = AlertDialog.Builder(context)
		alertDialogBuilder.setTitle(title)
		alertDialogBuilder.setMessage(message)
		alertDialogBuilder.setPositiveButton(R.string.dialog_yes) { _, _ -> onYes?.run() }
		alertDialogBuilder.setNegativeButton(R.string.dialog_no) { _, _ -> onNo?.run() }
		alertDialogBuilder.show()
	}
}