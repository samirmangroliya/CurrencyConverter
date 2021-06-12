package com.samir.paypaycodechallenge.restapicall

import androidx.lifecycle.MutableLiveData
import com.backbase.assignment.globaldata.GlobalConstant
import com.backbase.assignment.restapicall.RetrofitClient
import com.samir.paypaycodechallenge.models.ApiCurrencyList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ApiHelper {

    private var mLiveDataCurrencyList: MutableLiveData<ApiCurrencyList?>? = null

    fun getCurrencyList(): MutableLiveData<ApiCurrencyList?>? {
        try {
            val call = RetrofitClient.apiInterface.getCurrencyList(GlobalConstant.ACCESS_KEY);
            call?.enqueue(object : Callback<ApiCurrencyList?> {
                override fun onResponse(call: Call<ApiCurrencyList?>, response: Response<ApiCurrencyList?>) {
                    response.body()?.let {
                        mLiveDataCurrencyList?.postValue(it)
                    }
                }

                override fun onFailure(call: Call<ApiCurrencyList?>, t: Throwable) {
                    var apiCurrencyList = ApiCurrencyList()
                    apiCurrencyList.info = t.localizedMessage
                    mLiveDataCurrencyList?.postValue(apiCurrencyList)
                }
            })

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}