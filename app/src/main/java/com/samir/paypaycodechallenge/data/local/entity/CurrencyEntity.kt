package com.samir.paypaycodechallenge.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "CurrencyList", indices = [Index(value = ["abbr"], unique = true)])
data class CurrencyEntity(
    @PrimaryKey
    val abbr: String,
    val name: String?
)
