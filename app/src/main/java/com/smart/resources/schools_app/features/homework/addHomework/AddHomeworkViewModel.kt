package com.smart.resources.schools_app.features.homework.addHomework

import android.app.Application
import androidx.lifecycle.*
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.myTypes.*
import com.smart.resources.schools_app.features.homework.HomeworkModel
import com.smart.resources.schools_app.features.homework.HomeworkRepository
import com.smart.resources.schools_app.features.homework.PostHomeworkModel
import kotlinx.coroutines.*
import retrofit2.Response


class AddHomeworkViewModel  (application: Application) : AndroidViewModel(application) {
    private val c= application.applicationContext
    val postHomeworkModel=
        PostHomeworkModel()
    val postException= PostException()
    var uploadListener: PostListener?= null

    fun addHomework(){
        Logger.i(postHomeworkModel.toString())
        Logger.i("isDataValid: ${validateData()}")

        if(!validateData()) return

        uploadListener?.onUploadStarted()
        viewModelScope.launch {
                when (val myRes = GlobalScope.async { HomeworkRepository.addHomework(
                    postHomeworkModel
                ) as Response<HomeworkModel> }.toMyResult()) {
                    is Success -> uploadListener?.onUploadCompleted()

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