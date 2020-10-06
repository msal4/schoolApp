package com.smart.resources.schools_app.features.lecture

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.myTypes.*
import com.smart.resources.schools_app.features.users.data.StudentModel
import com.smart.resources.schools_app.features.users.data.UserRepository
import java.net.HttpURLConnection


class LectureViewModel(application: Application) : AndroidViewModel(application) {
    private val c = application.applicationContext

    val lectures: LiveData<List<LectureModel>> = liveData {
        emit(fetchLectures())
    }
    val listState = ListState()
    private val lectureRepository:ILectureRepository= LectureRepository()
    private val user= UserRepository.instance.getUser()

    private suspend fun fetchLectures(): List<LectureModel> {
        if(user == null || user !is StudentModel) return listOf()
        listState.apply {

            setLoading(true)
            when (val result = lectureRepository.getLectures(schoolId = user.schoolId,classId = user.classInfo.classId.toString())) {
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