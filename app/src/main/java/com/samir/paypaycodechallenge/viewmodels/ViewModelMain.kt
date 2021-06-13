package com.samir.paypaycodechallenge.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.samir.paypaycodechallenge.models.ApiCurrencyList
import com.samir.paypaycodechallenge.models.ApiCurrencyRate
import com.samir.paypaycodechallenge.models.Currency
import com.samir.paypaycodechallenge.models.Quotes
import com.samir.paypaycodechallenge.restapicall.ApiHelper

internal class ViewModelMain : ViewModel() {
    fun getCurrencyList(): MutableLiveData<ApiCurrencyList?> {
        return ApiHelper.getCurrencyList()
    }

    fun getCurrencyRates(): MutableLiveData<ApiCurrencyRate?> {
        return ApiHelper.getCurrencyRates()
    }

    fun currencyListToList(jsonObject: JsonObject): MutableList<Currency>? {
        try {
            val currencyList = mutableListOf<Currency>()
            for (key in jsonObject.keySet()) {
                val value = jsonObject.get(key)
                val currency = Currency(key, value.asString)
                currencyList.add(currency)
            }
            return currencyList
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun currencyRatesToList(jsonObject: JsonObject): MutableList<Quotes>? {
        try {
            val currencyRateList = mutableListOf<Quotes>()
            for (key in jsonObject.keySet()) {
                val value = jsonObject.get(key)
                val currency = Quotes(key, value.asDouble)
                currencyRateList.add(currency)
            }
            return currencyRateList
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun getCurrencyNameList(currencyList: MutableList<Currency>): MutableList<String> {
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