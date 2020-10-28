package com.smart.resources.schools_app.features.schedule

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


class ScheduleViewModel(application: Application) : AndroidViewModel(application) {
    private val c= application.applicationContext
    val listState = ListState()
    private val schedule: MutableLiveData<List<List<String?>>>
            by lazy { MutableLiveData<List<List<String?>>>() }

    fun getSchedule():
            LiveData<List<List<String?>>> {
        fetchSchedule()

        return schedule
    }

    private fun fetchSchedule(){
        viewModelScope.launch {
            listState.apply {
                setLoading(true)

                when (val result =  RetrofitHelper.scheduleClient.fetchSchedule() ) {
                    is Success -> {
                        if (result.data.isNullOrEmpty())
                            setBodyError(c.getString(R.string.no_schedule))
                        else {
                            setLoading(false)
                            schedule.value = result.data
                        }

                    }
                    is ResponseError -> setBodyError(result.combinedMsg)
                    is ConnectionError -> setBodyError(c.getString(R.string.connection_error))
                }
            }
        }
    }
}