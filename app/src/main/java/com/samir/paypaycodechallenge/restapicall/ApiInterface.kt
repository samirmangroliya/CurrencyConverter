package com.backbase.assignment.restapicall

import com.samir.paypaycodechallenge.models.ApiCurrencyList
import com.samir.paypaycodechallenge.models.ApiCurrencyRate
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiInterface {
    @GET("list")
    fun getCurrencyList(@Query("access_key") access_key: String?): Call<ApiCurrencyList?>?

    @GET
    fun getCurrencyRates(@Url url: String?): Call<ApiCurrencyRate?>?
}