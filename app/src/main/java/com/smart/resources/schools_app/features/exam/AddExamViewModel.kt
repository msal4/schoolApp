package com.smart.resources.schools_app.features.exam

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.myTypes.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class AddExamViewModel(application: Application) : AndroidViewModel(application) {
    private val c= application.applicationContext
    val postExamModel= PostExamModel()
    val postException= PostException()
    var listener: PostListener?= null

    fun addHomework(){

        Logger.i(postExamModel.toString())
        Logger.i("isDataValid: ${validateData()}")

        if(!validateData()) return

        listener?.onUploadStarted()
        viewModelScope.launch {
            when(val myRes= GlobalScope.async { ExamRepository.addExam(postExamModel) }.toMyResult()){
                is Success -> listener?.onUploadCompleted()
                is ResponseError -> listener?.onError(myRes.combinedMsg)
                is ConnectionError -> listener?.onError(c.getString(R.string.connection_error))
            }
        }
    }

    private fun validateData()=
        with(postExamModel){
            if(date != null) postException.dateMsg.postValue(null)
            if(type.isNotBlank()) postException.typeMsg.postValue(null)
            if(classId.isNotBlank()) postException.sectionMsg.postValue(null)

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
                type.isBlank() -> {
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