package com.smart.resources.schools_app.features.rating.addRarting

import android.app.Application
import androidx.lifecycle.*
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.helpers.BackendHelper
import com.smart.resources.schools_app.core.myTypes.*
import com.smart.resources.schools_app.features.rating.AddRatingModel
import com.smart.resources.schools_app.sharedUi.ListState
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


@Suppress("UNCHECKED_CAST")
class AddRatingViewModel(application: Application, private val postListener: PostListener) :
    AndroidViewModel(application) {
    private val c = application.applicationContext
    val listState = ListState().apply {
        setLoading(false)
        setBodyError(c.getString(R.string.select_section))
    }

    val sectionAndClassesErrorMsg: MutableLiveData<String> = MutableLiveData()
    var classId = MutableLiveData<String>()

    val addRatingModels: LiveData<MutableList<AddRatingModel>?> =
        classId.switchMap {
            liveData {
                emit(fetchStudents(it))
            }
        }

    private suspend fun fetchStudents(classId: String): MutableList<AddRatingModel>? {
        listState.setLoading(true)

        val studentsResult =
            viewModelScope.async { BackendHelper.studentDao.getStudentsByClass(classId) }
                .toMyResult()
        when (studentsResult) {
            is Success -> {
                if (studentsResult.data.isNullOrEmpty()) listState.setBodyError(c.getString(R.string.no_students))
                else {
                    listState.setLoading(false)
                    return studentsResult.data.map { AddRatingModel(it)}.toMutableList()
                }

            }
            is ResponseError -> listState.setBodyError(studentsResult.combinedMsg)
            is ConnectionError -> listState.setBodyError(c.getString(R.string.connection_error))
        }

        listState.setLoading(false)
        return null
    }

    fun addRatings() {
        Logger.i(addRatingModels.value?.firstOrNull().toString())
        resetErrors()

        if (!isDataValid()) return

        Logger.i("uploading started")
        postListener.onUploadStarted()
        viewModelScope.launch {
            val result =
                async { BackendHelper.ratingDao.addRatings(addRatingModels.value.orEmpty()) }
                    .toMyResult()

            when (result) {
                is Success -> postListener.onUploadCompleted()
                is ResponseError -> postListener.onError(result.combinedMsg)
                is ConnectionError -> postListener.onError(c.getString(R.string.connection_error))
            }
        }
    }

    private fun resetErrors() {
        if (!sectionAndClassesErrorMsg.value.isNullOrBlank()) sectionAndClassesErrorMsg.value = ""
    }

    private fun isDataValid(): Boolean {
        when {
            classId.value.isNullOrBlank() -> {
                sectionAndClassesErrorMsg.value= c.getString(R.string.field_required)
                return false
            }
            noOneRatedYet() -> {
                postListener.onError(c.getString(R.string.no_student_rated))
                return false
            }
        }
        return true
    }

    private fun noOneRatedYet() = addRatingModels.value?.find { it.isRated } == null

    class Factory(private val mApplication: Application, private val postListener: PostListener) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return AddRatingViewModel(mApplication, postListener) as T
        }

    }
}