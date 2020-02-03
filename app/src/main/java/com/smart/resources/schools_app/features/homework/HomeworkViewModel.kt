package com.smart.resources.schools_app.features.homework

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.helpers.BackendHelper
import com.smart.resources.schools_app.core.myTypes.*
import com.smart.resources.schools_app.features.login.CanLogout
import kotlinx.coroutines.*
import retrofit2.Response


class HomeworkViewModel (application: Application) : AndroidViewModel(application), CanLogout{
    val listState = ListState()
    var postHomeworkModel= PostHomeworkModel()
    val postException= PostException()
    var uploadListener: PostListener?= null

    private val homeworkRepo= HomeworkRepository()
    val getHomework: LiveData<MutableList<HomeworkModel>>
            by lazy {
                fetchHomework()
                homeworkRepo.homework}

    private val c= application.applicationContext
    var onError: ((String)-> Unit)?= null

    fun deleteHomework(position: Int){
        viewModelScope.launch {
            when(val result = homeworkRepo.deleteHomework(position)){
                is Success -> {

                }
                Unauthorized-> expireLogout(c)
                is ResponseError -> onError?.invoke(result.combinedMsg)
                is ConnectionError -> onError?.invoke(c.getString(R.string.connection_error))
            }
        }
    }


    private fun fetchHomework(){
        viewModelScope.launch {
            listState.setLoading(true)
            when (val result =homeworkRepo.downloadHomework()) {
                is Success -> {
                    if (result.data.isNullOrEmpty()) listState.setBodyError(c.getString(R.string.no_homework))
                    else listState.setLoading(false)
                }
                Unauthorized-> expireLogout(c)
                is ResponseError -> listState.setBodyError(result.combinedMsg)
                is ConnectionError -> listState.setBodyError(c.getString(R.string.connection_error))
            }
        }
    }



    fun addHomework(){
        Logger.i(postHomeworkModel.toString())
        Logger.i("isDataValid: ${validateData()}")

        if(!validateData()) return

        uploadListener?.onUploadStarted()
        viewModelScope.launch {
            when (val myRes = homeworkRepo.addHomework(postHomeworkModel)) {
                is Success -> {
                    uploadListener?.onUploadCompleted()
                    //postHomeworkModel= PostHomeworkModel()
                }
                Unauthorized-> expireLogout(c)
                is ResponseError -> uploadListener?.onError(myRes.combinedMsg)
                is ConnectionError -> uploadListener?.onError(c.getString(R.string.connection_error))
            }
        }
    }


    private fun validateData()=
        with(postHomeworkModel){
            if(date != null) postException.dateMsg.postValue(null)
            if(classId.isNotBlank()) postException.sectionMsg.postValue(null)

            when {
                subjectName.isBlank() -> {
                    postException.subjectNameMsg.postValue(c.getString(R.string.field_required))
                    false
                }
                date==null -> {
                    postException.dateMsg.postValue(c.getString(R.string.field_required))
                    false
                }
                assignmentName.isBlank() -> {
                    postException.typeMsg.postValue(c.getString(R.string.field_required))
                    false
                }

                classId.isBlank() -> {
                    postException.sectionMsg.postValue(c.getString(R.string.field_required))
                    false
                }
                else -> {
                    true
                }
            }
        }


}

