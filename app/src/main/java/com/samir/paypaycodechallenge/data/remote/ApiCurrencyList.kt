package com.samir.paypaycodechallenge.data.remote

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

class ApiCurrencyList : BaseResponse() {
    @SerializedName("currencies")
    val jsonObjectCurrencies: JsonObject? = null
}