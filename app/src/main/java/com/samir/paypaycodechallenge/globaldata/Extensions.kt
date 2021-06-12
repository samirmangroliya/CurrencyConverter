package com.backbase.assignment.globaldata

import android.content.Context
import android.view.Gravity
import android.widget.Toast

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
