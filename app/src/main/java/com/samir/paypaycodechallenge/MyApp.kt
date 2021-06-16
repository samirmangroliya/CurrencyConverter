package com.samir.paypaycodechallenge

import android.app.Application
import com.samir.paypaycodechallenge.data.local.database.CurrencyDatabase

class MyApp : Application() {
    val database by lazy { CurrencyDatabase.getInstance(this) }

    companion object {
        lateinit var instance: MyApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}