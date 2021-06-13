package com.samir.paypaycodechallenge.models

import com.google.gson.annotations.SerializedName

open class BaseResponse {
    @SerializedName("success")
    val success: Boolean = false

    @SerializedName("error")
    val error: Error? = null

    inner class Error {
        @SerializedName("code")
        var code: Int? = null

        @SerializedName("type")
        var type: String? = null

        @SerializedName("info")
        var info: String? = null
    }
}