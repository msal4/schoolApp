package com.smart.resources.schools_app.features.exam

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smart.resources.schools_app.core.helpers.BackendHelper
import com.smart.resources.schools_app.core.myTypes.MyResult
import com.smart.resources.schools_app.core.myTypes.toMyResult
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class AddExamViewModel : ViewModel() {

    var onComplete: ((MyResult<Unit>)->Unit)?= null

    private val examsDao: ExamDao = BackendHelper
        .retrofitWithAuth
        .create(ExamDao::class.java)


    fun addExams(examPostModel: ExamPostModel){
        viewModelScope.launch {
            val result = GlobalScope.async { examsDao.addExam(examPostModel) }.toMyResult()


            onComplete?.let { it(result) }


        }
    }

}