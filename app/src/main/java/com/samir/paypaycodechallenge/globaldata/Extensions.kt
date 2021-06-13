package com.samir.paypaycodechallenge.globaldata

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.samir.paypaycodechallenge.R

fun Context.showToast(strMsg: String?, duration: Int = Toast.LENGTH_SHORT) {
    try {
        if (listOfNotNull(this, strMsg).size == 2) {
            val toast = Toast.makeText(this, strMsg, duration)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
fun Activity.showAlert(
    msg: String?,
    listener: DialogInterface.OnClickListener? = null,
    title: String? = getString(R.string.app_name)
) {
    try {
        if (listOfNotNull(this, msg).size == 2) {
            AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton("Ok", listener)
                .create()
                .show()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Activity.hideSoftKeyboard(view: View?) {
    try {
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        view?.let {
            imm.hideSoftInputFromWindow(view.applicationWindowToken, 0)
        } ?: kotlin.run {
            imm.hideSoftInputFromWindow(this.currentFocus?.applicationWindowToken, 0)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
