package com.smart.resources.schools_app.features.exam

import androidx.lifecycle.MutableLiveData
import com.smart.resources.schools_app.core.extentions.notifyObservers
import com.smart.resources.schools_app.core.myTypes.MyResult
import com.smart.resources.schools_app.core.myTypes.Success
import com.smart.resources.schools_app.core.network.RetrofitHelper
import com.smart.resources.schools_app.features.users.data.UserRepository

class ExamRepository{
    val exams: MutableLiveData<MutableList<ExamModel>> = MutableLiveData(mutableListOf())

    suspend fun downloadExams(): MyResult<List<ExamModel>> {
        val isStudent= UserRepository.instance.getCurrentUserAccount()?.userType==0

        val myRes= getExamsResult(isStudent)
        if(myRes is Success) exams.value= myRes.data?.toMutableList()
        return  myRes
    }

    private suspend fun getExamsResult(isStudent: Boolean) =
      with(RetrofitHelper.examClient) { if (isStudent) fetchExams() else fetchTeacherExams() }

    suspend fun addExam(postExamModel: PostExamModel): MyResult<ExamModel> {
        val myRes= RetrofitHelper.examClient.addExam(postExamModel)
        if(myRes is Success){
            myRes.data?.let {
                exams.value?.add(0, it)
                exams.notifyObservers()
            }
        }

        return myRes
    }

}