package com.smart.resources.schools_app.features.homeworkSolution.domain.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.smart.resources.schools_app.core.myTypes.*
import com.smart.resources.schools_app.features.homeworkSolution.domain.model.HomeworkSolutionModel
import com.smart.resources.schools_app.features.login.CanLogout
import org.threeten.bp.LocalDateTime


class HomeworkSolutionViewModel(application: Application) : AndroidViewModel(application), CanLogout {
    private val c = application.applicationContext

    val answers: LiveData<List<HomeworkSolutionModel>> = liveData {
        emit(fetchLectures())
    }
    val listState = ListState()
    //private val lectureRepository:ILectureRepository= LectureRepository()


    private suspend fun fetchLectures(): List<HomeworkSolutionModel> {
        listState.apply {

            setLoading(true)
//            when (val result = lectureRepository.getLectures(schoolId = "2",classId = "3")) {
//                is Success -> {
//                    if (result.data.isNullOrEmpty()) setBodyError(c.getString(R.string.no_lectures))
//                    else {
//                        setLoading(false)
//                        return result.data
//                    }
//                }
//                Unauthorized -> expireLogout(c)
//                is ResponseError -> {
//                    val msg= if(result.statusCode == HttpURLConnection.HTTP_NOT_FOUND) c.getString(R.string.no_lectures) else result.combinedMsg
//                    setBodyError(msg)
//                }
//                is ConnectionError -> setBodyError(c.getString(R.string.connection_error))
//            }

            setLoading(false)
        }


        return listOf(
            HomeworkSolutionModel("1", "Ahmed", "This is the answer", "https://www.youtube.com/", LocalDateTime.now()),
            HomeworkSolutionModel("2", "Ahmed", "This is the answer", "https://www.youtube.com/" , LocalDateTime.now()),
            HomeworkSolutionModel("3", "Ahmed", "This is the answer", "https://www.youtube.com/", LocalDateTime.now())
            )
    }

}