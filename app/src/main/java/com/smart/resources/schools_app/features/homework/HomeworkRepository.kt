package com.smart.resources.schools_app.features.homework

import androidx.lifecycle.MutableLiveData
import com.smart.resources.schools_app.core.typeConverters.retrofit.dateTimeBackendSendFormatter
import com.smart.resources.schools_app.core.extentions.asBodyPart
import com.smart.resources.schools_app.core.extentions.asRequestBody
import com.smart.resources.schools_app.core.extentions.notifyObservers
import com.smart.resources.schools_app.core.utils.RetrofitHelper
import com.smart.resources.schools_app.core.myTypes.MyResult
import com.smart.resources.schools_app.core.myTypes.Success
import com.smart.resources.schools_app.core.myTypes.toMyResult
import com.smart.resources.schools_app.features.homeworkSolution.data.model.HomeworkSolutionModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class HomeworkRepository {
    val homework: MutableLiveData<MutableList<HomeworkModel>> = MutableLiveData(mutableListOf())


    suspend fun getTeacherHomework(): MyResult<List<HomeworkModel>> {
        val myRes = GlobalScope.async { RetrofitHelper.homeworkClient.fetchHomework() }.toMyResult()
        if (myRes is Success) homework.value = myRes.data?.toMutableList()
        return myRes
    }

    suspend fun getStudentHomework(): MyResult<List<HomeworkWithSolution>> {
        val myRes = GlobalScope.async { RetrofitHelper.homeworkClient.fetchHomeworkWithSolution() }
            .toMyResult()
        if (myRes is Success) homework.value = myRes.data?.map { it.homework.copy(solution = it.solution) }?.toMutableList() ?: mutableListOf()
        return myRes

    }

    fun addHomeworkSolution(
        homeworkSolution: HomeworkSolutionModel
    ) {
        homework.value= homework.value?.map {
            if (it.idHomework == homeworkSolution.homeworkId) it.copy(
                solution = homeworkSolution
            )
            else it
        }?.toMutableList()
    }

    suspend fun deleteHomework(position: Int): MyResult<Unit> {
        val model = homework.value?.get(position)

        val myRes =
            GlobalScope.async { RetrofitHelper.homeworkClient.deleteHomework(model?.idHomework) }
                .toMyResult()
        if (myRes is Success) {
            myRes.data?.let {
                homework.value?.removeAt(position)
                homework.notifyObservers()
            }
        }

        return myRes
    }

    suspend fun addHomework(postHomeworkModel: PostHomeworkModel): MyResult<HomeworkModel> {
        val myRes = GlobalScope.async { fireRequest(postHomeworkModel) }.toMyResult()
        if (myRes is Success) {
            myRes.data?.let {
                homework.value?.add(0, it)
                homework.notifyObservers()
            }
        }

        return myRes
    }

    private suspend fun fireRequest(postHomeworkModel: PostHomeworkModel) =
        with(postHomeworkModel) {
            date?.format(dateTimeBackendSendFormatter)?.asRequestBody()?.run {
                RetrofitHelper.homeworkClient.addHomework(
                    subjectName.asRequestBody(),
                    assignmentName.asRequestBody(),
                    this,
                    note.asRequestBody(),
                    attachment?.asBodyPart("attachment"),
                    classId.asRequestBody()
                )
            }
        }
}