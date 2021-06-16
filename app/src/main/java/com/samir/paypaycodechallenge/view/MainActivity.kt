package com.samir.paypaycodechallenge.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.work.WorkInfo
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.samir.paypaycodechallenge.R
import com.samir.paypaycodechallenge.adapter.RatesAdapter
import com.samir.paypaycodechallenge.data.local.entity.CurrencyEntity
import com.samir.paypaycodechallenge.data.remote.Utils
import com.samir.paypaycodechallenge.databinding.ActivityMainBinding
import com.samir.paypaycodechallenge.globaldata.hideSoftKeyboard
import com.samir.paypaycodechallenge.globaldata.showToast
import com.samir.paypaycodechallenge.viewmodels.ViewModelMain

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModelMain: ViewModelMain
    private var currencyList: MutableList<CurrencyEntity>? = null
    private lateinit var currencyAdapter: RatesAdapter
    private var jsonObjectRate: JsonObject? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindUI()

        fetchCurrencyData()
    }

    private fun bindUI() {
        //Binding and ToolBar
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        //viewModel
        viewModelMain = ViewModelProvider(this).get(ViewModelMain::class.java)

        //clear focus
        binding.contentlayout.etamount.clearFocus()
    }

    private fun fetchCurrencyData() {
        try {
            viewModelMain.fetchData(this)
            viewModelMain.getOutputWorkInfo().observe(this, { listOfWorkInfo ->
                if (listOfWorkInfo.isNullOrEmpty()) {
                    return@observe
                }

                val workInfo = listOfWorkInfo[0]
                Log.d("Work Info state:: ", "${workInfo.state}")

                if (workInfo.state == WorkInfo.State.ENQUEUED) {
                    showProgress(false)
                    //observe Room db
                    viewModelMain.getLocalCurrencyList(this)?.observe(this, { listOfCurrency ->
                        if (listOfCurrency != null && listOfCurrency.isNotEmpty()) {
                            currencyList = listOfCurrency as MutableList<CurrencyEntity>
                            showCurrencyList()
                        }
                    })

                    viewModelMain.getLocalCurrencyRate(this)?.observe(this, { listOfRates ->
                        if (listOfRates != null && listOfRates.isNotEmpty()) {
                            jsonObjectRate = Gson().fromJson(listOfRates[0].jsonObjectRate, JsonObject::class.java)
                        }
                    })
                } else {
                    showProgress(true)
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun showProgress(isShow: Boolean) {
        binding.contentlayout.progressCircular.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    private fun showCurrencyList() {
        currencyList?.let {
            // set currency list to spinner
            val currencyAbbrList = Utils.getCurrencyNameList(it)
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
                showToast(getString(R.string.enter_amount_error))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}