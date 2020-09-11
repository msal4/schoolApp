package com.smart.resources.schools_app.features.homeworkAnswer

import com.smart.resources.schools_app.core.myTypes.MyResult
import com.smart.resources.schools_app.core.myTypes.Success
import com.smart.resources.schools_app.features.homeworkAnswer.addHomeworkAnswer.AddHomeworkAnswerModel
import com.smart.resources.schools_app.features.homeworkAnswer.getHomeworkAnswers.HomeworkAnswerModel

class HomeworkAnswerRepository : IHomeworkAnswerRepository {
    override suspend fun getHomeworkAnswers(homeworkId: String): MyResult<List<HomeworkAnswerModel>> {
        return Success(listOf())
    }

    override suspend fun addAnswer(
        studentId: String,
        addHomeworkAnswerModel: AddHomeworkAnswerModel
    ) {

    }
}