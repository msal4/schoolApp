package com.smart.resources.schools_app.features.homeworkSolution.domain.repository

import com.smart.resources.schools_app.core.myTypes.MyResult
import com.smart.resources.schools_app.features.homeworkSolution.domain.model.AddHomeworkSolutionModel
import com.smart.resources.schools_app.features.homeworkSolution.domain.model.HomeworkSolutionModel

interface IHomeworkSolutionRepository {
    suspend fun getSolution(homeworkId:String): MyResult<HomeworkSolutionModel>
    suspend fun addSolution(studentId: String, homeworkId: String, addHomeworkSolutionModel: AddHomeworkSolutionModel): MyResult<HomeworkSolutionModel>
}