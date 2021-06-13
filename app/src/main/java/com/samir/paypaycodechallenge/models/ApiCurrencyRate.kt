package com.samir.paypaycodechallenge.models

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

class ApiCurrencyRate : BaseResponse() {
    @SerializedName("timestamp")
    val timestamp: Long? = null

    @SerializedName("quotes")
    val jsonObjectQuotes: JsonObject? = null
}