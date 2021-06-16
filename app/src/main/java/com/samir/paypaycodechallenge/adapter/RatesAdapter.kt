package com.samir.paypaycodechallenge.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonObject
import com.samir.paypaycodechallenge.R
import com.samir.paypaycodechallenge.data.local.entity.CurrencyEntity
import com.samir.paypaycodechallenge.globaldata.CurrencyUtil

class RatesAdapter(
    private val selectedCurrency: CurrencyEntity,
    private val amount: Double,
    private val items: MutableList<CurrencyEntity>,
    private val jsonObjectRate: JsonObject?
) :
    RecyclerView.Adapter<RatesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_currency_rate,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var tvName: TextView
        private lateinit var tvRate: TextView

        fun bind(currency: CurrencyEntity?) = with(itemView) {
            tvName = itemView.findViewById(R.id.tvname)
            tvRate = itemView.findViewById(R.id.tvrate)

            currency?.run {
                try {
                    tvName.text = abbr

                    val currAbbrUSD = "USD$abbr"
                    val currUSDRate = jsonObjectRate?.get(currAbbrUSD)?.asDouble

                    val selectedAbbrUSD = "USD${selectedCurrency.abbr}"
                    val selectedUSDRate = jsonObjectRate?.get(selectedAbbrUSD)?.asDouble

                    Log.d("Currency Rate:: ", "$abbr >> $currUSDRate")
                    Log.d("Selected Rate:: ", selectedCurrency.abbr + " >> " + selectedUSDRate)

                    if (currAbbrUSD == selectedAbbrUSD) {
                        tvRate.text = "1"
                    } else {
                        val rate = CurrencyUtil().currencyConverter(currUSDRate, selectedUSDRate, amount)
                        Log.d("Currency Final:: ", "FinalRate >> $rate")
                        tvRate.text = "$rate"
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        }
    }
}