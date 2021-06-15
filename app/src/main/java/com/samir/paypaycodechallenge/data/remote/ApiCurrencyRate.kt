package com.samir.paypaycodechallenge.data.remote

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import java.util.*

class ApiCurrencyRate : BaseResponse() {
    @SerializedName("timestamp")
    val timestamp: Long = Date().time

    @SerializedName("quotes")
    val jsonObjectQuotes: JsonObject? = null
}