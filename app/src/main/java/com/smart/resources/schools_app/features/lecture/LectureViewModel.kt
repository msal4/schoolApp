package com.smart.resources.schools_app.features.lecture

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.myTypes.ConnectionError
import com.smart.resources.schools_app.core.myTypes.ListState
import com.smart.resources.schools_app.core.myTypes.ResponseError
import com.smart.resources.schools_app.core.myTypes.Success
import com.smart.resources.schools_app.features.users.data.model.userInfo.StudentInfoModel
import com.smart.resources.schools_app.features.users.data.model.userInfo.UserInfoModel
import com.smart.resources.schools_app.features.users.domain.usecase.IGetCurrentUserModelUseCase
import kotlinx.coroutines.launch
import java.net.HttpURLConnection


class LectureViewModel @ViewModelInject constructor(
    application: Application,
    private val getCurrentUserModel: IGetCurrentUserModelUseCase,
) : AndroidViewModel(application) {
    private val c = application.applicationContext

    val listState = ListState()
    private val lectureRepository:ILectureRepository= LectureRepository()
    private val studentModel: LiveData<UserInfoModel?> = liveData {
        val userModel= getCurrentUserModel()
        if(userModel is StudentInfoModel) emit(userModel)
    }

    private val _lectures: MutableLiveData<List<LectureModel>> = MutableLiveData()
//    val lectures: LiveData<List<LectureModel>> = studentModel.switchMap {
//        viewModelScope.launch {
//            _lectures.postValue(fetchLectures(it, subjectId))
//        }
//        _lectures
//    }

    fun getLectures(subjectId: Int): LiveData<List<LectureModel>> {
        return studentModel.switchMap {
            viewModelScope.launch {
                _lectures.postValue(fetchLectures(it as StudentInfoModel, subjectId))
            }
            _lectures
        }
    }

    private suspend fun fetchLectures(studentModel: StudentInfoModel, subjectId: Int): List<LectureModel> {
        listState.apply {

            setLoading(true)
            when (val result = lectureRepository.getLectures(subjectId)) {
                is Success -> {
                    if (result.data.isNullOrEmpty()) setBodyError(c.getString(R.string.no_lectures))
                    else {
                        setLoading(false)
                        return result.data
                    }
                }
                is ResponseError -> {
                    val msg= if(result.statusCode == HttpURLConnection.HTTP_NOT_FOUND) c.getString(R.string.no_lectures) else result.combinedMsg
                    setBodyError(msg)
                }
                is ConnectionError -> setBodyError(c.getString(R.string.connection_error))
            }
        }

        return listOf()
    }

}