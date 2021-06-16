package com.samir.paypaycodechallenge.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.work.*
import com.samir.paypaycodechallenge.R
import com.samir.paypaycodechallenge.data.local.entity.CurrencyEntity
import com.samir.paypaycodechallenge.data.local.entity.CurrencyRateEntity
import com.samir.paypaycodechallenge.data.remote.SyncDataWorker
import com.samir.paypaycodechallenge.globaldata.SYNC_DATA_WORK_NAME
import com.samir.paypaycodechallenge.globaldata.TAG_SYNC_DATA
import com.samir.paypaycodechallenge.globaldata.isInternetAvailable
import com.samir.paypaycodechallenge.globaldata.showToast
import com.samir.paypaycodechallenge.repository.CurrencyRepository
import java.util.concurrent.TimeUnit


internal class ViewModelMain : ViewModel() {

    private lateinit var workManager: WorkManager
    private lateinit var savedWorkInfo: LiveData<List<WorkInfo>>

    fun getLocalCurrencyList(context: Context): LiveData<List<CurrencyEntity>>? {
        val currencyRepo = CurrencyRepository(context)
        return currencyRepo.allCurrencyList?.asLiveData()
    }

    fun getLocalCurrencyRate(context: Context): LiveData<List<CurrencyRateEntity>>? {
        val currencyRepo = CurrencyRepository(context)
        return currencyRepo.allCurrencyRate?.asLiveData()
    }

    fun fetchData(context: Context) {
        if (!isInternetAvailable(context)) {
            context.showToast(context.getString(R.string.internet_error))
            return
        }

        workManager = WorkManager.getInstance(context)
        savedWorkInfo = workManager.getWorkInfosByTagLiveData(TAG_SYNC_DATA)
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val periodicSyncDataWork = PeriodicWorkRequest.Builder(SyncDataWorker::class.java, 15, TimeUnit.MINUTES)
            .addTag(TAG_SYNC_DATA)
            .setConstraints(constraints)
            .setBackoffCriteria(BackoffPolicy.LINEAR, PeriodicWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
            .build()
        workManager.enqueueUniquePeriodicWork(
            SYNC_DATA_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            periodicSyncDataWork
        )
    }

    fun getOutputWorkInfo(): LiveData<List<WorkInfo>> {
        return savedWorkInfo
    }

}