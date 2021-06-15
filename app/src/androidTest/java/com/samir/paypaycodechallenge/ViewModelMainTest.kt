package com.samir.paypaycodechallenge

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.samir.paypaycodechallenge.data.remote.ApiCurrencyList
import com.samir.paypaycodechallenge.viewmodels.ViewModelMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ViewModelMainTest {
    private lateinit var viewModelMain: ViewModelMain
    private val apiCurrencyList = ApiCurrencyList()
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModelMain = ViewModelMain()
        apiCurrencyList.success = true
    }

    @Test
    fun testWhenLiveDataUpdated() {
        Assert.assertEquals(viewModelMain.getCurrencyList().value?.success, true)
    }
}