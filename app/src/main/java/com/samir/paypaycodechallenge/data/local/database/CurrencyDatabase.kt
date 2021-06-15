package com.samir.paypaycodechallenge.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.samir.paypaycodechallenge.data.local.dao.CurrencyDao
import com.samir.paypaycodechallenge.data.local.entity.CurrencyEntity
import com.samir.paypaycodechallenge.data.local.dao.CurrencyRateDao
import com.samir.paypaycodechallenge.data.local.entity.CurrencyRateEntity
import com.samir.paypaycodechallenge.data.local.converter.DateTypeConverter
import com.samir.paypaycodechallenge.globaldata.DATABASE_NAME

@Database(
    entities = [CurrencyEntity::class, CurrencyRateEntity::class], version = 1, exportSchema = false
)
@TypeConverters(DateTypeConverter::class)
abstract class CurrencyDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
    abstract fun currencyRateDao(): CurrencyRateDao

    companion object {
        @Volatile
        private var INSTANCE: CurrencyDatabase? = null

        fun getInstance(context: Context): CurrencyDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                CurrencyDatabase::class.java, DATABASE_NAME
            ).fallbackToDestructiveMigration().build()
    }
}
