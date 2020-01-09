package com.smart.resources.schools_app.features.homework

import androidx.lifecycle.ViewModel
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.core.util.*


class AddHomeworkViewModel : ViewModel() {
   val postHomeworkModel= PostHomeworkModel()


    private val homeworkDao: HomeworkDao = BackendHelper
        .retrofitWithAuth
        .create(HomeworkDao::class.java)

    fun addHomework(){

        Logger.i(postHomeworkModel.toString())

        Logger.i("isDataValid: ${validateData()}")

//        postHomeworkModel.apply {
//            homeworkDao.addHomework(
//                subjectName.asRequestBody(),
//                assignmentName.asRequestBody(),
//                date,
//                note.asRequestBody(),
//                ,
//                classId.asRequestBody()
//            )
//        }
    }

    private fun validateData()=
        with(postHomeworkModel){
            when {
                subjectName.isBlank() -> {
                    false
                }
                assignmentName.isBlank() -> {
                    false
                }
                note.isBlank() -> {
                    false
                }
                date==null -> {
                    false
                }
                classId.isBlank() -> {
                    false
                }
                else -> {
                    true
                }
            }
        }
}