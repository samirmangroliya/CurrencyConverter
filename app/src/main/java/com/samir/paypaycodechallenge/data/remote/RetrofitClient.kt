package com.samir.paypaycodechallenge.data.remote

import com.samir.paypaycodechallenge.globaldata.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private val retrofitClient: Retrofit.Builder by lazy {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val okhttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okhttpClient)
            .addConverterFactory(GsonConverterFactory.create())
    }

    val apiInterface: ApiInterface by lazy {
        retrofitClient
            .build()
            .create(ApiInterface::class.java)
    }
}