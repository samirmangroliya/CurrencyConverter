package com.samir.paypaycodechallenge

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.samir.paypaycodechallenge.data.local.dao.CurrencyDao
import com.samir.paypaycodechallenge.data.local.database.CurrencyDatabase
import com.samir.paypaycodechallenge.data.local.entity.CurrencyEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class RoomDatabaseTest {
    private lateinit var currencyDao: CurrencyDao
    private lateinit var db: CurrencyDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, CurrencyDatabase::class.java
        ).build()
        currencyDao = db.currencyDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeUserAndReadInList() = runBlocking {
        val listOfCurrency = mutableListOf<CurrencyEntity>()
        val currencyEntityINR = CurrencyEntity("INR", "Indian Rupees")
        //val currencyEntityUSD = CurrencyEntity("USD", "US Dollar") //test for negative result
        listOfCurrency.add(currencyEntityINR)

        currencyDao.clearAll()
        currencyDao.insertAll(listOfCurrency)

        val allCurrencies = currencyDao.getAllCurrency().first()
        assertEquals("Currency insert database is failed...", allCurrencies[0], currencyEntityINR)
    }

    @Test
    @Throws(Exception::class)
    fun deleteAll() = runBlocking {
        val listOfCurrency = mutableListOf<CurrencyEntity>()

        val currencyEntityINR = CurrencyEntity("INR", "Indian Rupees")
        val currencyEntityUSD = CurrencyEntity("USD", "US Dollar")

        listOfCurrency.add(currencyEntityINR)
        listOfCurrency.add(currencyEntityUSD)
        currencyDao.insertAll(listOfCurrency)

        currencyDao.clearAll()

        val allCurrency = currencyDao.getAllCurrency().first()
        Assert.assertTrue(allCurrency.isEmpty())
    }
}
