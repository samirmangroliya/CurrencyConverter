package com.samir.paypaycodechallenge.data.remote

import androidx.lifecycle.MutableLiveData
import com.samir.paypaycodechallenge.globaldata.ACCESS_KEY
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ApiHelper {
    fun getCurrencyList(): MutableLiveData<ApiCurrencyList?> {
        val mLiveDataCurrencyList: MutableLiveData<ApiCurrencyList?> = MutableLiveData<ApiCurrencyList?>()
        try {
            val call = RetrofitClient.apiInterface.getCurrencyList(ACCESS_KEY)
            call?.enqueue(object : Callback<ApiCurrencyList?> {
                override fun onResponse(call: Call<ApiCurrencyList?>, response: Response<ApiCurrencyList?>) {
                    response.body()?.let {
                        mLiveDataCurrencyList.postValue(it)
                    }
                }

                override fun onFailure(call: Call<ApiCurrencyList?>, t: Throwable) {
                    val apiCurrencyList = ApiCurrencyList()
                    t.localizedMessage?.let { apiCurrencyList.error?.info = it }
                    mLiveDataCurrencyList.postValue(apiCurrencyList)
                }
            })

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return mLiveDataCurrencyList
    }

    fun getCurrencyRates(): MutableLiveData<ApiCurrencyRate?> {
        val mLiveDataCurrencyRate: MutableLiveData<ApiCurrencyRate?> = MutableLiveData<ApiCurrencyRate?>()
        try {
            val call = RetrofitClient.apiInterface.getCurrencyRates(ACCESS_KEY)
            call?.enqueue(object : Callback<ApiCurrencyRate?> {
                override fun onResponse(call: Call<ApiCurrencyRate?>, response: Response<ApiCurrencyRate?>) {
                    response.body()?.let {
                        mLiveDataCurrencyRate.postValue(it)
                    }
                }

                override fun onFailure(call: Call<ApiCurrencyRate?>, t: Throwable) {
                    val apiCurrencyList = ApiCurrencyRate()
                    t.localizedMessage?.let { apiCurrencyList.error?.info = it }
                    mLiveDataCurrencyRate.postValue(apiCurrencyList)
                }
            })

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return mLiveDataCurrencyRate
    }
}