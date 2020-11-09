package com.smart.resources.schools_app.features.advertising

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.myTypes.ConnectionError
import com.smart.resources.schools_app.core.myTypes.ListState
import com.smart.resources.schools_app.core.myTypes.ResponseError
import com.smart.resources.schools_app.core.myTypes.Success
import com.smart.resources.schools_app.core.network.RetrofitHelper
import kotlinx.coroutines.launch


class HomeworkViewModel(application: Application) : AndroidViewModel(application) {
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

                when (val result =  RetrofitHelper.advertisingClient.fetchAdvertisements()) {
                    is Success -> {
                        if (result.data.isNullOrEmpty())
                            setBodyError(c.getString(R.string.no_advertisements))
                        else {
                            setLoading(false)
                            advertisements.value = result.data
                        }

                    }
                    is ResponseError -> setBodyError(result.combinedMsg)
                    is ConnectionError -> setBodyError(c.getString(R.string.connection_error))
                }
            }
        }
    }
}