package com.samir.paypaycodechallenge.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.samir.paypaycodechallenge.data.local.entity.CurrencyRateEntity

@Dao
interface CurrencyRateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRate(currencyRateEntity: CurrencyRateEntity)

    @Query("SELECT * FROM CurrencyRate")
    fun getRate(): List<CurrencyRateEntity>

    @Query("DELETE FROM CurrencyRate")
    fun clearAll()
}
