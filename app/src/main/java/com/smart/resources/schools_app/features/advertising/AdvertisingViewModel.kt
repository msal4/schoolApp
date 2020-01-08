package com.smart.resources.schools_app.features.advertising

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smart.resources.schools_app.core.util.*
import kotlinx.coroutines.*

typealias AdvertisingResult= MyResult<List<AdvertisingModel>>

class HomeworkViewModel : ViewModel() {
    private val advertisements: MutableLiveData<AdvertisingResult>
            by lazy { MutableLiveData<AdvertisingResult>() }

    fun getExams():
            LiveData<AdvertisingResult> {
        fetchAdvertisements()

        return advertisements
    }


    private val advertisingDao: AdvertisingDao = BackendHelper
        .retrofitWithAuth
        .create(AdvertisingDao::class.java)


    private fun fetchAdvertisements(){
        viewModelScope.launch {
            val result = GlobalScope.async { advertisingDao.fetchAdvertisements() }.toMyResult()
            advertisements.value = result
        }
    }
}