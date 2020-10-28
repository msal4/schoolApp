package com.smart.resources.schools_app.features.absence.getAbsence

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
import com.smart.resources.schools_app.features.absence.StudentAbsenceModel
import kotlinx.coroutines.launch


class AbsenceViewModel(application: Application) : AndroidViewModel(application) {

    private val c= application.applicationContext
    val listState = ListState()
    private val absences: MutableLiveData<List<StudentAbsenceModel>> = MutableLiveData<List<StudentAbsenceModel>>()

    init {
        fetchAbsences()
    }
    fun getAbsence(): LiveData<List<StudentAbsenceModel>> {
        return absences
    }

    private fun fetchAbsences(){
        viewModelScope.launch {
            listState.setLoading(true)
            when (val result = RetrofitHelper.absenceClient.fetchStudentAbsence()) {
                is Success -> {
                    if (result.data.isNullOrEmpty()) listState.setBodyError(c.getString(R.string.no_student_absence))
                    else {
                        absences.postValue(result.data)
                        listState.setLoading(false)
                    }
                }
                is ResponseError -> listState.setBodyError(result.combinedMsg)
                is ConnectionError -> listState.setBodyError(c.getString(R.string.connection_error))
            }
        }
    }
}