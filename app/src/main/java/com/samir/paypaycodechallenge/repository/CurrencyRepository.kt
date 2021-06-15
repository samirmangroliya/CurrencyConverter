package com.samir.paypaycodechallenge.repository

import android.content.Context
import com.samir.paypaycodechallenge.data.local.dao.CurrencyDao
import com.samir.paypaycodechallenge.data.local.dao.CurrencyRateDao
import com.samir.paypaycodechallenge.data.local.database.CurrencyDatabase.Companion.getInstance
import com.samir.paypaycodechallenge.data.local.entity.CurrencyEntity
import com.samir.paypaycodechallenge.data.local.entity.CurrencyRateEntity
import com.samir.paypaycodechallenge.models.Currency
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class CurrencyRepository(context: Context) {
    private val currencyDao: CurrencyDao
    private val currencyRateDao: CurrencyRateDao


    init {
        val db = getInstance(context)
        currencyDao = db.currencyDao()
        currencyRateDao = db.currencyRateDao()
       // getListData()
    }

   /* fun getListData(): List<CurrencyEntity> {
        var allData: List<CurrencyEntity> = currencyDao.allCurrency()
        return allData
    }*/

    fun insertData(currencies: List<Currency>) {
        val executor: ExecutorService = Executors.newSingleThreadExecutor()
        executor.execute {
            try {
                currencyDao.clearAll()
                val currencyEntityList = ArrayList<CurrencyEntity>()
                for (currency in currencies) {
                    val currencyEntity = CurrencyEntity(currency.abbr, currency.name)
                    currencyEntityList.add(currencyEntity)
                }
                currencyDao.insertAll(currencyEntityList)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun insertRatesData(timestamp: Long, currencyRate: String) {
        val executor: ExecutorService = Executors.newSingleThreadExecutor()
        executor.execute {
            try {
                currencyRateDao.clearAll()
                val currencyRateEntity = CurrencyRateEntity(timestamp, currencyRate)
                currencyRateDao.insertRate(currencyRateEntity)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}