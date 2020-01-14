package com.smart.resources.schools_app.features.homework

import android.app.Application
import androidx.lifecycle.*
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.helpers.BackendHelper
import com.smart.resources.schools_app.core.myTypes.*
import kotlinx.coroutines.*


class HomeworkViewModel (application: Application) : AndroidViewModel(application) {
    val listState = ListState()

    private val homework: MutableLiveData<List<HomeworkModel>>
            by lazy { MutableLiveData<List<HomeworkModel>>() }

    val getHomework: LiveData<List<HomeworkModel>>
            by lazy { homework }

    private val c= application.applicationContext
    var onError: ((String)-> Unit)?= null

    fun fetchHomework() {
        viewModelScope.launch {
            listState.setLoading(true)
            when (val result =
                GlobalScope.async { BackendHelper.homeworkDao.fetchHomework() }.toMyResult()) {
                is Success -> {
                    if (result.data.isNullOrEmpty()) listState.setBodyError(c.getString(R.string.no_homework))
                    else {
                        listState.setLoading(false)
                        homework.postValue(result.data)
                    }
                }
                is ResponseError -> listState.setBodyError(result.combinedMsg)
                is ConnectionError -> listState.setBodyError(c.getString(R.string.connection_error))
            }
        }
    }
    }



