package com.samir.paypaycodechallenge.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CurrencyRate")
data class CurrencyRateEntity(@PrimaryKey val timestamp: Long, val jsonObjectRate: String)
