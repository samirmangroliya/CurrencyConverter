package com.samir.paypaycodechallenge.data.remote

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.samir.paypaycodechallenge.MyApp
import com.samir.paypaycodechallenge.data.local.dao.CurrencyDao
import com.samir.paypaycodechallenge.data.local.dao.CurrencyRateDao
import com.samir.paypaycodechallenge.data.local.entity.CurrencyEntity
import com.samir.paypaycodechallenge.data.local.entity.CurrencyRateEntity
import com.samir.paypaycodechallenge.globaldata.ACCESS_KEY

class SyncDataWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {
    private val currencyDao: CurrencyDao
    private val currencyRateDao: CurrencyRateDao

    init {
        val myApp = applicationContext as MyApp
        currencyDao = myApp.database.currencyDao()
        currencyRateDao = myApp.database.currencyRateDao()
    }

    override fun doWork(): Result {
        try {
            val callList = RetrofitClient.apiInterface.getCurrencyList(ACCESS_KEY)
            val responseList = callList.execute()

            val callRate = RetrofitClient.apiInterface.getCurrencyRates(ACCESS_KEY)
            val responseRate = callRate.execute()

            if (responseList.isSuccessful && responseList.body() != null && responseList.body()?.jsonObjectCurrencies != null &&
                responseRate.isSuccessful && responseRate.body() != null && responseRate.body()?.jsonObjectQuotes != null
            ) {

                //currency rate insert
                val currencyEntityList = mutableListOf<CurrencyEntity>()
                val jsonObjectCurrency = responseList.body()?.jsonObjectCurrencies
                Log.d("CurrencyList:: ", jsonObjectCurrency.toString())
                jsonObjectCurrency?.let {
                    for (key in it.keySet()) {
                        val value = it.get(key)
                        val currencyEntity = CurrencyEntity(key, value.asString)
                        currencyEntityList.add(currencyEntity)
                    }
                    currencyDao.clearAll()
                    Log.d("CurrencyList:: ", currencyEntityList.size.toString())
                    currencyDao.insertAll(currencyEntityList)
                }

                //currency rate insert
                val responseBodyRate = responseRate.body()
                val currencyRateEntity =
                    responseBodyRate?.timestamp?.let { CurrencyRateEntity(it, responseBodyRate.jsonObjectQuotes.toString()) }

                currencyRateEntity?.let {
                    currencyRateDao.clearAll()
                    currencyRateDao.insertRate(it)
                }

                return Result.success()

            } else {
                return Result.retry()
            }
        } catch (e: Throwable) {
            e.printStackTrace()
            Log.e("Worker", "Error fetching data", e)
            return Result.failure()
        }
    }

    override fun onStopped() {
        super.onStopped()
        Log.i("Worker", "OnStopped called for this worker")
    }
}