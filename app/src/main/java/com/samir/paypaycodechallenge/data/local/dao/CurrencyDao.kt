package com.samir.paypaycodechallenge.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.samir.paypaycodechallenge.data.local.entity.CurrencyEntity

@Dao
interface CurrencyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(currencyList: List<CurrencyEntity>)

    @Query("SELECT * FROM CurrencyList")
    fun allCurrency(): List<CurrencyEntity>

    @Query("DELETE FROM CurrencyList")
    fun clearAll()

    @Query("SELECT COUNT(*) FROM CurrencyList")
    fun getCurrencyCount(): Int

}
