package com.smart.resources.schools_app.features.homework

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.core.adapters.dateTimeBackendSendFormatter
import com.smart.resources.schools_app.core.helpers.BackendHelper
import com.smart.resources.schools_app.core.myTypes.MyResult
import com.smart.resources.schools_app.core.myTypes.Success
import com.smart.resources.schools_app.core.myTypes.toMyResult
import com.smart.resources.schools_app.core.utils.asBodyPart
import com.smart.resources.schools_app.core.utils.asRequestBody
import com.smart.resources.schools_app.core.utils.notifyObservers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class HomeworkRepository{
     val homework: MutableLiveData<MutableList<HomeworkModel>> = MutableLiveData()


    suspend fun downloadHomework(): MyResult<List<HomeworkModel>>{
        val myRes= GlobalScope.async {  BackendHelper.homeworkDao.fetchHomework()}.toMyResult()
        if(myRes is Success) homework.value= myRes.data?.toMutableList()
        Logger.i("sotp")
        return  myRes
    }

    suspend fun deleteHomework(position:Int): MyResult<Unit> {
        val model= homework.value?.get(position)

        val myRes= GlobalScope.async {BackendHelper.homeworkDao.deleteHomework(model?.idHomework)}.toMyResult()
        if(myRes is Success){
            myRes.data?.let {
                homework.value?.removeAt(position)
                homework.notifyObservers()
            }
        }else{
            homework.notifyObservers()
        }

        return myRes
    }
    
    suspend fun addHomework(postHomeworkModel: PostHomeworkModel): MyResult<HomeworkModel> {
            val myRes= GlobalScope.async {fireRequest(postHomeworkModel)}.toMyResult()
            if(myRes is Success){
                myRes.data?.let {
                    homework.value?.add(0, it)
                    homework.notifyObservers()
                }
            }

            return myRes
    }

    private suspend fun fireRequest(postHomeworkModel: PostHomeworkModel) =
        with( postHomeworkModel) {
            date?.format(dateTimeBackendSendFormatter)?.asRequestBody()?.run {
                BackendHelper.homeworkDao.addHomework(
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