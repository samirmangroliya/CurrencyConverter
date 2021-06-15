package com.samir.paypaycodechallenge.view

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.gson.JsonObject
import com.samir.paypaycodechallenge.adapter.RatesAdapter
import com.samir.paypaycodechallenge.databinding.ActivityMainBinding
import com.samir.paypaycodechallenge.globaldata.hideSoftKeyboard
import com.samir.paypaycodechallenge.globaldata.showAlert
import com.samir.paypaycodechallenge.globaldata.showToast
import com.samir.paypaycodechallenge.models.Currency
import com.samir.paypaycodechallenge.models.Quotes
import com.samir.paypaycodechallenge.viewmodels.ViewModelMain

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModelMain: ViewModelMain
    private var currencyList: MutableList<Currency>? = null
    private var currencyRatesList: MutableList<Quotes>? = null
    private lateinit var currencyAdapter: RatesAdapter
    private var jsonObjectRate: JsonObject? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUI()

        //api call for get list of currency
        getCurrencyList()
    }

    private fun initUI() {
        //Binding and ToolBar
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        //viewModel
        viewModelMain = ViewModelProvider(this).get(ViewModelMain::class.java)

        //clear focus
        binding.contentlayout.etamount.clearFocus()
    }

    private fun getCurrencyList() {
        try {
            showProgress(true)
            viewModelMain.getCurrencyList().observe(this, {
                showProgress(false)
                it?.let { apiCurrencyList ->
                    getCurrencyRates()
                    if (apiCurrencyList.success) {
                        currencyList =
                            apiCurrencyList.jsonObjectCurrencies?.let { jsonObject ->
                                viewModelMain.currencyListToList(
                                    jsonObject
                                )
                            }

                        if (!currencyList.isNullOrEmpty()) {
                            viewModelMain.insertDataLocally(this, currencyList)
                        }
                        showCurrencyList()
                    } else {
                        showAlert(apiCurrencyList.error?.info)
                    }
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun showProgress(isShow: Boolean) {
        binding.contentlayout.progressCircular.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    private fun getCurrencyRates() {
        try {
            showProgress(true)
            viewModelMain.getCurrencyRates().observe(this, {
                showProgress(false)
                it?.let { apiCurrencyRate ->
                    if (apiCurrencyRate.success) {
                        jsonObjectRate = apiCurrencyRate.jsonObjectQuotes
                        currencyRatesList =
                            it.jsonObjectQuotes?.let { jsonObject -> viewModelMain.currencyRatesToList(jsonObject) }
                        if (jsonObjectRate?.isJsonObject == true) {
                            viewModelMain.insertDataRatesLocally(this, apiCurrencyRate.timestamp, jsonObjectRate.toString())
                        }
                    }
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun showCurrencyList() {
        currencyList?.let {
            // set currency list to spinner
            val currencyAbbrList = viewModelMain.getCurrencyNameList(it)
            binding.contentlayout.spinner.adapter =
                ArrayAdapter(this@MainActivity, android.R.layout.simple_list_item_1, currencyAbbrList)

            //show UI
            binding.contentlayout.etamount.visibility = View.VISIBLE
            binding.contentlayout.spinner.visibility = View.VISIBLE
            binding.contentlayout.btnGetRate.visibility = View.VISIBLE
            binding.contentlayout.rvCurrencyRates.visibility = View.VISIBLE
            binding.contentlayout.btnGetRate.setOnClickListener {
                showCurrencyRates()
            }
        }
    }

    private fun showCurrencyRates() {
        try {
            val inputValue = binding.contentlayout.etamount.text.toString()
            val position = binding.contentlayout.spinner.selectedItemPosition

            if (inputValue.isNotBlank()) {
                hideSoftKeyboard(binding.contentlayout.etamount)
                val amount = inputValue.toDouble()
                currencyList?.let {
                    val selectedCurrency = it[position]
                    currencyAdapter = RatesAdapter(selectedCurrency, amount, it, jsonObjectRate)
                    binding.contentlayout.rvCurrencyRates.adapter = currencyAdapter
                }
            } else {
                binding.contentlayout.etamount.requestFocus()
                showToast("Please Enter Amount")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}