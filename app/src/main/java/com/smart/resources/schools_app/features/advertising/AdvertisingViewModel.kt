package com.smart.resources.schools_app.features.advertising

import android.app.Application
import androidx.lifecycle.*
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.helpers.BackendHelper
import com.smart.resources.schools_app.core.myTypes.*
import com.smart.resources.schools_app.features.login.CanLogout
import kotlinx.coroutines.*


class HomeworkViewModel(application: Application) : AndroidViewModel(application), CanLogout {
    private val c= application.applicationContext
    val listState = ListState()

    private val advertisements: MutableLiveData<List<AdvertisingModel>>
            by lazy { MutableLiveData<List<AdvertisingModel>>() }

    fun getExams():
            LiveData<List<AdvertisingModel>> {
        fetchAdvertisements()

        return advertisements
    }


    private fun fetchAdvertisements(){
        viewModelScope.launch {
            listState.apply {
                setLoading(true)

                val result = GlobalScope.async { BackendHelper.advertisingDao.fetchAdvertisements() }.toMyResult()
                when (result) {
                    is Success -> {
                        if (result.data.isNullOrEmpty())
                            setBodyError(c.getString(R.string.no_advertisements))
                        else {
                            setLoading(false)
                            advertisements.value = result.data
                        }

                    }
                    Unauthorized-> expireLogout(c)
                    is ResponseError -> setBodyError(result.combinedMsg)
                    is ConnectionError -> setBodyError(c.getString(R.string.connection_error))
                }
            }
        }
    }
}