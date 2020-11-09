package com.smart.resources.schools_app.features.homework

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.myTypes.*
import com.smart.resources.schools_app.features.homeworkSolution.data.model.HomeworkSolutionModel
import com.smart.resources.schools_app.features.users.data.UserRepository
import kotlinx.coroutines.launch

class HomeworkViewModel @ViewModelInject constructor(application: Application) : AndroidViewModel(application){
    val listState = ListState()
    var postHomeworkModel= PostHomeworkModel()
    val postException= PostException()
    var uploadListener: PostListener?= null
    val isStudent = liveData {
        emit(UserRepository.instance.getCurrentUserAccount()?.isStudent == true)
    }

    private val homeworkRepo= HomeworkRepository()

    val homework: LiveData<MutableList<HomeworkModel>> =  isStudent.switchMap {
        fetchHomework(it)
        homeworkRepo.homework
    }

    private val c= application.applicationContext
    var onError: ((String)-> Unit)?= null

    fun deleteHomework(position: Int){
        viewModelScope.launch {
            when(val result = homeworkRepo.deleteHomework(position)){
                is Success -> {

                }
                is ResponseError -> onError?.invoke(result.combinedMsg)
                is ConnectionError -> onError?.invoke(c.getString(R.string.connection_error))
            }
        }
    }

    private fun fetchHomework(isStudent:Boolean){
        viewModelScope.launch {
            listState.setLoading(true)
            when (val result =if(isStudent)  homeworkRepo.getStudentHomework() else homeworkRepo.getTeacherHomework()) {
                is Success -> {
                    if (result.data.isNullOrEmpty()) listState.setBodyError(c.getString(R.string.no_homework))
                    else listState.setLoading(false)
                }
                is ResponseError -> listState.setBodyError(result.combinedMsg)
                is ConnectionError -> listState.setBodyError(c.getString(R.string.connection_error))
            }
        }
    }

    fun addSolution(homeworkSolutionModel: HomeworkSolutionModel){
        homeworkRepo.addHomeworkSolution(homeworkSolutionModel)
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

