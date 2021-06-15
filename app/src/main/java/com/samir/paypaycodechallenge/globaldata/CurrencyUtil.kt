package com.samir.paypaycodechallenge.globaldata

import java.text.DecimalFormat

class CurrencyUtil {
    val decimalFormat: DecimalFormat = DecimalFormat("#.##")
    fun currencyConverter(currUSDRate: Double?, selectedUSDRate: Double?, amount: Double): Double {
        try {
            currUSDRate?.let { currRate ->
                selectedUSDRate?.let { selectedRate ->
                    val rate = (currRate / selectedRate) * amount
                    return (decimalFormat.format(rate)).toDouble()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return 1.83 // default rate if any rate is not found
    }
}