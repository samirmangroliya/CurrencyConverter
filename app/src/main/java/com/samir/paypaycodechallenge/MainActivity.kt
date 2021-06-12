package com.samir.paypaycodechallenge

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.backbase.assignment.globaldata.showToast
import com.samir.paypaycodechallenge.databinding.ActivityMainBinding
import com.samir.paypaycodechallenge.viewmodels.ViewModelMain

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModelMain: ViewModelMain

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        //viewmodel
        viewModelMain = ViewModelProvider(this).get(ViewModelMain::class.java)

        //api call for get list of currency
        getCurrencyList()
    }

    private fun getCurrencyList() {
        try {
            viewModelMain.getCurrencyList()?.observe(this, { apiCurrencyList ->
                if (apiCurrencyList?.success == true) {
                    apiCurrencyList?.jsonObject?.let {
                        //var arrayCurrencyList = mutableListOf<Currency>()
                    }
                    print(" Json list :: ${apiCurrencyList.jsonObject}")
                } else {
                    showToast(apiCurrencyList?.info)
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}