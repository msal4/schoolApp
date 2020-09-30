package com.smart.resources.schools_app.features.homeworkSolution.domain.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.myTypes.*
import com.smart.resources.schools_app.features.homeworkSolution.data.repository.HomeworkSolutionRepository
import com.smart.resources.schools_app.features.homeworkSolution.data.model.HomeworkSolutionModel
import com.smart.resources.schools_app.features.homeworkSolution.domain.repository.IHomeworkSolutionRepository
import com.smart.resources.schools_app.features.login.CanLogout
import java.net.HttpURLConnection


class HomeworkSolutionViewModel(application: Application) : AndroidViewModel(application),
    CanLogout {
    private val c = application.applicationContext

    val answers: LiveData<List<HomeworkSolutionModel>> = liveData {
        emit(getSolutions())
    }
    private val solutionRepo: IHomeworkSolutionRepository = HomeworkSolutionRepository()
    val listState = ListState()

    private var mHomeworkId= ""

    fun init(homeworkId:String){
        mHomeworkId= homeworkId
    }

    private suspend fun getSolutions(): List<HomeworkSolutionModel> {
        listState.apply {

            setLoading(true)
            when (val result = solutionRepo.getSolution(mHomeworkId)) {
                is Success -> {
                    if (result.data.isNullOrEmpty()) setBodyError(c.getString(R.string.no_solutions))
                    else {
                        setLoading(false)
                        return result.data
                    }
                }
                is ResponseError -> {
                    val msg =
                        if (result.statusCode == HttpURLConnection.HTTP_NOT_FOUND) c.getString(R.string.no_solutions) else result.combinedMsg
                    setBodyError(msg)
                }
                is ConnectionError -> setBodyError(c.getString(R.string.connection_error))
            }

            setLoading(false)
        }
        return emptyList()

//        return listOf(
//            HomeworkSolutionModel("1", "Ahmed", "This is the answer", "https://www.youtube.com/", LocalDateTime.now()),
//            HomeworkSolutionModel("2", "Ahmed", "This is the answer", "https://www.youtube.com/" , LocalDateTime.now()),
//            HomeworkSolutionModel("3", "Ahmed", "This is the answer", "https://www.youtube.com/", LocalDateTime.now())
//            )
    }
}