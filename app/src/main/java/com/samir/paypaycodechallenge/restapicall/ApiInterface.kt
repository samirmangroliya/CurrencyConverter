package com.samir.paypaycodechallenge.restapicall

import com.samir.paypaycodechallenge.models.ApiCurrencyList
import com.samir.paypaycodechallenge.models.ApiCurrencyRate
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("list")
    fun getCurrencyList(@Query("access_key") access_key: String?): Call<ApiCurrencyList?>?

    @GET("live")
    fun getCurrencyRates(@Query("access_key") access_key: String?): Call<ApiCurrencyRate?>?
}