package com.samir.paypaycodechallenge.data.remote

import com.google.gson.annotations.SerializedName

open class BaseResponse {
    @SerializedName("success")
    var success: Boolean = false

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