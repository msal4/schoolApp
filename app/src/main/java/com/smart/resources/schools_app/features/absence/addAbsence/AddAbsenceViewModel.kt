package com.smart.resources.schools_app.features.absence.addAbsence

import android.app.Application
import androidx.lifecycle.*
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.network.RetrofitHelper
import com.smart.resources.schools_app.core.myTypes.*
import com.smart.resources.schools_app.features.absence.AddAbsenceModel
import com.smart.resources.schools_app.features.absence.PostAbsenceModel
import com.smart.resources.schools_app.core.myTypes.ListState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


@Suppress("UNCHECKED_CAST")
class AddAbsenceViewModel(application: Application, private val postListener: PostListener) :
    AndroidViewModel(application) {
    private val c = application.applicationContext
    val listState = ListState().apply {
        setLoading(false)
        setBodyError(c.getString(R.string.select_section))
    }

    val addAbsenceException: AddAbsenceException = AddAbsenceException()

    val postAbsenceModel = PostAbsenceModel()
    var classId = MutableLiveData<String>()

    val addAbsenceModels: LiveData<List<AddAbsenceModel>?> =
        classId.switchMap {
            liveData {
                postAbsenceModel.classId= it
                emit(fetchStudents(it))
            }
        }

    private suspend fun fetchStudents(classId: String): List<AddAbsenceModel>? {
        listState.setLoading(true)

        Logger.i("absence model: $postAbsenceModel")
        val studentsResult =
            viewModelScope.async { RetrofitHelper.studentClient.getStudentsMarksByClass(classId) }
                .toMyResult()

        when (studentsResult) {
            is Success -> {
                if (studentsResult.data.isNullOrEmpty()) listState.setBodyError(c.getString(R.string.no_students))
                else {
                    listState.setLoading(false)
                    return studentsResult.data.map { AddAbsenceModel(it.id, name = it.name) }
                }

            }
            is ResponseError -> listState.setBodyError(studentsResult.combinedMsg)
            is ConnectionError -> listState.setBodyError(c.getString(R.string.connection_error))
        }

        return null
    }

    fun addAbsences() {
        Logger.i(addAbsenceModels.value?.firstOrNull().toString())
        addAbsenceException.safeReset()
        fillStudents()


        if (!isDataValid()) return
        Logger.i("uploading started")
        postListener.onUploadStarted()
        viewModelScope.launch {
            val result =
                GlobalScope.async { RetrofitHelper.absenceClient.addStudentAbsences(postAbsenceModel) }.toMyResult()

            when (result) {
                is Success -> postListener.onUploadCompleted()
                is ResponseError -> postListener.onError(result.combinedMsg)
                is ConnectionError -> postListener.onError(c.getString(R.string.connection_error))
            }
        }
    }

    private fun fillStudents() {
        addAbsenceModels.value?.filter { it.isChecked }?.map { it.idStudent }.let {
            postAbsenceModel.studentsId= it?: listOf()
        }
    }

    private fun isDataValid(): Boolean {
        when {
            postAbsenceModel.classId.isBlank() -> {
                addAbsenceException.sectionAndClassesMsg.value= c.getString(R.string.field_required)
                return false
            }
            postAbsenceModel.subjectName.isBlank() -> {
                addAbsenceException.subjectMsg.value = c.getString(R.string.field_required)
                return false
            }
            postAbsenceModel.studentsId.isEmpty() -> {
                postListener.onError(c.getString(R.string.no_student_selected))
                return false
            }
        }
        return true
    }


    class Factory(private val mApplication: Application, private val postListener: PostListener) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return AddAbsenceViewModel(mApplication, postListener) as T
        }

    }
}