package com.smart.resources.schools_app.features.exam

import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.core.helpers.BackendHelper
import com.smart.resources.schools_app.core.helpers.SharedPrefHelper
import com.smart.resources.schools_app.core.myTypes.MyResult
import com.smart.resources.schools_app.core.myTypes.Success
import com.smart.resources.schools_app.core.myTypes.UserType
import com.smart.resources.schools_app.core.myTypes.toMyResult
import com.smart.resources.schools_app.core.utils.notifyObservers
import com.smart.resources.schools_app.features.homework.HomeworkModel
import com.smart.resources.schools_app.features.profile.AccountManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import retrofit2.Response

class ExamRepository{
    val exams: MutableLiveData<MutableList<ExamModel>> = MutableLiveData(mutableListOf())

    suspend fun downloadExams(): MyResult<List<ExamModel>> {
        val isStudent= AccountManager.instance?.getCurrentUser()?.userType==0

        val myRes= getExamsResult(isStudent)
        if(myRes is Success) exams.value= myRes.data?.toMutableList()
        return  myRes
    }

    private suspend fun getExamsResult(isStudent: Boolean) =
        GlobalScope.async { with(BackendHelper.examDao) { if (isStudent) fetchExams() else fetchTeacherExams() } }.toMyResult()

    suspend fun addExam(postExamModel: PostExamModel): MyResult<ExamModel> {
        val myRes= GlobalScope.async {BackendHelper.examDao.addExam(postExamModel)}.toMyResult()
        if(myRes is Success){
            myRes.data?.let {
                exams.value?.add(0, it)
                exams.notifyObservers()
            }
        }

        return myRes
    }

}