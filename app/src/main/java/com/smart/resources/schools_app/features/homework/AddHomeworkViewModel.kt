package com.smart.resources.schools_app.features.homework

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.myTypes.*
import com.smart.resources.schools_app.core.myTypes.PostListener
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Response


class AddHomeworkViewModel(application: Application) : AndroidViewModel(application) {
    private val c= application.applicationContext
    val postHomeworkModel= PostHomeworkModel()
    val postException= PostException()
    var listener: PostListener?= null

    fun addHomework(){

        Logger.i(postHomeworkModel.toString())
        Logger.i("isDataValid: ${validateData()}")

        if(!validateData()) return

        listener?.onUploadStarted()
        viewModelScope.launch {
            val response= HomeworkRepository.addHomework(postHomeworkModel)
            if(response== null)  listener?.onError(c.getString(R.string.data_conversion_error))
            else {
                when (val myRes = GlobalScope.async { response as Response<Unit> }.toMyResult()) {
                    is Success -> listener?.onUploadComplete()
                    is ResponseError -> listener?.onError(myRes.combinedMsg)
                    is ConnectionError -> listener?.onError(myRes.message)
                }
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