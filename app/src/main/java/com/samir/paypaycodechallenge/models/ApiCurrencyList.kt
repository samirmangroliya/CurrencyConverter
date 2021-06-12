package com.samir.paypaycodechallenge.models

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

class ApiCurrencyList {
    @SerializedName("success")
    val success: Boolean = false

    @SerializedName("info")
    var info: String = ""

    @SerializedName("currencies")
    val jsonObject: JsonObject? = null
}