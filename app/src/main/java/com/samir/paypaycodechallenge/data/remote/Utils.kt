package com.samir.paypaycodechallenge.data.remote

import com.samir.paypaycodechallenge.data.local.entity.CurrencyEntity

object Utils {
    fun getCurrencyNameList(currencyList: MutableList<CurrencyEntity>): MutableList<String> {
        val currencyAbbrList = mutableListOf<String>()
        try {
            for (currency in currencyList) {
                currencyAbbrList.add("${currency.abbr} - ${currency.name}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return currencyAbbrList
    }
}