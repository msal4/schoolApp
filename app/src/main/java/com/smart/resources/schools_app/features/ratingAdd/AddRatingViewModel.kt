package com.smart.resources.schools_app.features.ratingAdd

import android.app.Application
import androidx.lifecycle.*
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.helpers.BackendHelper
import com.smart.resources.schools_app.core.myTypes.*
import com.smart.resources.schools_app.features.rating.RatingModel
import com.smart.resources.schools_app.core.myTypes.ListState
import com.smart.resources.schools_app.features.login.CanLogout
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class AddRatingViewModel(application: Application, private val postListener: PostListener) :
    AndroidViewModel(application), CanLogout {
    private val c = application.applicationContext
    val listState = ListState().apply {
        setLoading(false)
        setBodyError(c.getString(R.string.select_section))
    }

    val sectionAndClassesErrorMsg: MutableLiveData<String> = MutableLiveData()
    var classId = MutableLiveData<String>()

    val ratingModels: LiveData<MutableList<RatingModel>?> =
        classId.switchMap {
            liveData {
                emit(fetchStudents(it))
            }
        }

    private suspend fun fetchStudents(classId: String): MutableList<RatingModel>? {
        listState.setLoading(true)

        val studentsResult =
            viewModelScope.async { BackendHelper.studentService.getStudentsByClass(classId) }
                .toMyResult()
        when (studentsResult) {
            is Success -> {
                if (studentsResult.data.isNullOrEmpty()) listState.setBodyError(c.getString(R.string.no_students))
                else {
                    listState.setLoading(false)
                    return studentsResult.data.map { RatingModel(it)}.toMutableList()
                }

            }
            Unauthorized-> expireLogout(c)
            is ResponseError -> listState.setBodyError(studentsResult.combinedMsg)
            is ConnectionError -> listState.setBodyError(c.getString(R.string.connection_error))
        }

        listState.setLoading(false)
        return null
    }

    fun addRatings() {
        Logger.i(ratingModels.value?.firstOrNull().toString())
        resetErrors()

        if (!isDataValid()) return

        Logger.i("uploading started")

        getRatings()?.let {
            postListener.onUploadStarted()
            viewModelScope.launch {
                Logger.i("ratings: $it")
                val result =
                    async { BackendHelper.ratingDao.addRatings(it) }
                        .toMyResult()

                when (result) {
                    is Success -> postListener.onUploadCompleted()
                    Unauthorized-> expireLogout(c)
                    is ResponseError -> postListener.onError(result.combinedMsg)
                    is ConnectionError -> postListener.onError(c.getString(R.string.connection_error))
                }
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

    private fun getRatings() = ratingModels.value?.filter { it.isRated }
    private fun noOneRatedYet() = ratingModels.value?.find { it.isRated } == null

    class Factory(private val mApplication: Application, private val postListener: PostListener) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return AddRatingViewModel(mApplication, postListener) as T
        }

    }
}