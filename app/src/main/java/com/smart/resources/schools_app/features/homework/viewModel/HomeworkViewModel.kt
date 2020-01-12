package com.smart.resources.schools_app.features.homework.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.helpers.BackendHelper
import com.smart.resources.schools_app.core.myTypes.*
import com.smart.resources.schools_app.features.homework.model.HomeworkModel
import kotlinx.coroutines.*


class HomeworkViewModel (application: Application) : AndroidViewModel(application) {
    private val homework: MutableLiveData<List<HomeworkModel>>
            by lazy { MutableLiveData<List<HomeworkModel>>() }

    val getHomework: LiveData<List<HomeworkModel>>
            by lazy { homework }

    private val c= application.applicationContext
    var onError: ((String)-> Unit)?= null

    fun fetchHomework(){
        viewModelScope.launch {
            when (val result = GlobalScope.async { BackendHelper.homeworkDao.fetchHomework() }.toMyResult()) {
                is Success -> {
                    if (result.data.isNullOrEmpty()) onError?.invoke(c.getString(R.string.no_homework))
                    else { homework.postValue(result.data) }

                }
                is ResponseError -> onError?.invoke(result.combinedMsg)
                is ConnectionError -> onError?.invoke(c.getString(R.string.connection_error))
            }
        }
    }

}