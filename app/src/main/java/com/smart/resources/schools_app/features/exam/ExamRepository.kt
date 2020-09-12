package com.smart.resources.schools_app.features.exam

import androidx.lifecycle.MutableLiveData
import com.smart.resources.schools_app.core.helpers.RetrofitHelper
import com.smart.resources.schools_app.core.myTypes.MyResult
import com.smart.resources.schools_app.core.myTypes.Success
import com.smart.resources.schools_app.core.myTypes.toMyResult
import com.smart.resources.schools_app.core.extentions.notifyObservers
import com.smart.resources.schools_app.features.users.UsersRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class ExamRepository{
    val exams: MutableLiveData<MutableList<ExamModel>> = MutableLiveData(mutableListOf())

    suspend fun downloadExams(): MyResult<List<ExamModel>> {
        val isStudent= UsersRepository.instance.getCurrentUserAccount()?.userType==0

        val myRes= getExamsResult(isStudent)
        if(myRes is Success) exams.value= myRes.data?.toMutableList()
        return  myRes
    }

    private suspend fun getExamsResult(isStudent: Boolean) =
        GlobalScope.async { with(RetrofitHelper.examClient) { if (isStudent) fetchExams() else fetchTeacherExams() } }.toMyResult()

    suspend fun addExam(postExamModel: PostExamModel): MyResult<ExamModel> {
        val myRes= GlobalScope.async {RetrofitHelper.examClient.addExam(postExamModel)}.toMyResult()
        if(myRes is Success){
            myRes.data?.let {
                exams.value?.add(0, it)
                exams.notifyObservers()
            }
        }

        return myRes
    }

}