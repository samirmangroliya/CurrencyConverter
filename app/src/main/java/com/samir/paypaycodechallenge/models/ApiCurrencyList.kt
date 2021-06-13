package com.samir.paypaycodechallenge.models

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

class ApiCurrencyList : BaseResponse() {
    @SerializedName("currencies")
    val jsonObjectCurrencies: JsonObject? = null
}