package com.smart.resources.schools_app.features.homeworkAnswer

import com.smart.resources.schools_app.core.myTypes.MyResult
import com.smart.resources.schools_app.features.homeworkAnswer.addHomeworkAnswer.AddHomeworkAnswerModel
import com.smart.resources.schools_app.features.homeworkAnswer.getHomeworkAnswers.HomeworkAnswerModel

interface IHomeworkAnswerRepository {
    suspend fun getHomeworkAnswers(homeworkId:String): MyResult<List<HomeworkAnswerModel>>
    suspend fun addAnswer(studentId: String, addHomeworkAnswerModel: AddHomeworkAnswerModel)
}