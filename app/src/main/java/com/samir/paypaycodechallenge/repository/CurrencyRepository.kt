package com.samir.paypaycodechallenge.repository

import android.content.Context
import com.samir.paypaycodechallenge.data.local.dao.CurrencyDao
import com.samir.paypaycodechallenge.data.local.dao.CurrencyRateDao
import com.samir.paypaycodechallenge.data.local.database.CurrencyDatabase.Companion.getInstance
import com.samir.paypaycodechallenge.data.local.entity.CurrencyEntity
import com.samir.paypaycodechallenge.data.local.entity.CurrencyRateEntity
import kotlinx.coroutines.flow.Flow


class CurrencyRepository(context: Context) {
    private val currencyDao: CurrencyDao
    private val currencyRateDao: CurrencyRateDao
    var allCurrencyList: Flow<List<CurrencyEntity>>? = null
    var allCurrencyRate: Flow<List<CurrencyRateEntity>>? = null

    init {
        val db = getInstance(context)
        currencyDao = db.currencyDao()
        currencyRateDao = db.currencyRateDao()
        allCurrencyList = currencyDao.getAllCurrency()
        allCurrencyRate = currencyRateDao.getRate()
    }

}