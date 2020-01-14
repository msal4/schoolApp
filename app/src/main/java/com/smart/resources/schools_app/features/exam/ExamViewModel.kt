package com.smart.resources.schools_app.features.exam

import android.app.Application
import androidx.lifecycle.*
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.helpers.BackendHelper
import com.smart.resources.schools_app.core.helpers.SharedPrefHelper
import com.smart.resources.schools_app.core.myTypes.*
import com.smart.resources.schools_app.core.utils.hide
import kotlinx.coroutines.*


class ExamViewModel(application: Application) : AndroidViewModel(application) {
    private val c= application.applicationContext

    private val exams: MutableLiveData<List<ExamModel>>
            by lazy { MutableLiveData<List<ExamModel>>()
            }

    val listState= ListState()


    fun getExams():
            LiveData<List<ExamModel>> {

        return exams
    }

    fun fetchExams(){
        val isStudent= SharedPrefHelper.instance?.userType== UserType.STUDENT

        viewModelScope.launch {
            listState.apply {

                setLoading(true)
                val result = GlobalScope.async {
                    with(BackendHelper.examDao) { if (isStudent)fetchExams() else fetchTeacherExams() }
                }.toMyResult()

                when (result) {
                    is Success -> {
                        if (result.data.isNullOrEmpty())
                            setBodyError(c.getString(R.string.no_exams))
                        else {
                            setLoading(false)
                            exams.value = result.data
                        }

                    }
                    is ResponseError -> setBodyError(result.combinedMsg)
                    is ConnectionError -> setBodyError(c.getString(R.string.connection_error))
                }
            }
        }
    }

}