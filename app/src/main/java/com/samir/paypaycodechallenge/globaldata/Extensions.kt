package com.samir.paypaycodechallenge.globaldata

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
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

fun isInternetAvailable(context: Context): Boolean {
    try {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            val nwInfo = connectivityManager.activeNetworkInfo ?: return false
            return nwInfo.isConnected
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return false
}
