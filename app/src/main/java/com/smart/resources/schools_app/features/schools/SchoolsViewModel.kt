package com.smart.resources.schools_app.features.schools

import android.app.Application
import androidx.lifecycle.*
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.helpers.BackendHelper
import com.smart.resources.schools_app.core.myTypes.*
import com.smart.resources.schools_app.features.login.CanLogout
import kotlinx.coroutines.*

class SchoolsViewModel(application: Application) : AndroidViewModel(application),
    CanLogout {
    private val c= application.applicationContext
    val listState = ListState()

    private val schools: MutableLiveData<List<School>>
            by lazy { MutableLiveData<List<School>>() }

    fun getSchools():
            LiveData<List<School>> {
        fetchSchools()

        return schools
    }

    private fun fetchSchools(){
        listState.apply {
            setLoading(true)
        viewModelScope.launch {

            when (val result = GlobalScope.async { BackendHelper.accountDao.getSchools() }.toMyResult()) {
                is Success -> {
                    if (result.data.isNullOrEmpty()) setBodyError(c.getString(R.string.no_schools))
                    else {
                        schools.value= result.data
                        setLoading(false)
                    }
                }
                Unauthorized -> expireLogout(c)
                is ResponseError -> setBodyError(result.combinedMsg)
                is ConnectionError ->  setBodyError(c.getString(R.string.connection_error))
            }
        }

        }
    }
}