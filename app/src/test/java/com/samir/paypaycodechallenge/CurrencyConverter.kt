package com.samir.paypaycodechallenge

import com.samir.paypaycodechallenge.globaldata.CurrencyUtil
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CurrencyConverter {
    //Test for 10 indian rupees to Japan currency

    private var amount: Double = 0.0
    private var currentJPYUSDRate: Double? = null
    private var selectedINRUSDRate: Double? = null

    @Before
    fun setUp() {
        amount = 10.0 // amount
        currentJPYUSDRate = 110.126968 // 1 usd to jpy
        selectedINRUSDRate = 73.250986 // 1 usd to inr
    }

    @Test
    fun currencyConverterTest() {
        val rate = CurrencyUtil().currencyConverter(currentJPYUSDRate, selectedINRUSDRate, amount)
        assertEquals("Rate converter is failed...", 15.03, rate, 0.0)
    }
}