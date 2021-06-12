package com.samir.paypaycodechallenge.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.samir.paypaycodechallenge.models.ApiCurrencyList
import com.samir.paypaycodechallenge.restapicall.ApiHelper

internal class ViewModelMain : ViewModel() {
    fun getCurrencyList(): MutableLiveData<ApiCurrencyList?>? {
        return ApiHelper.getCurrencyList()
    }
}