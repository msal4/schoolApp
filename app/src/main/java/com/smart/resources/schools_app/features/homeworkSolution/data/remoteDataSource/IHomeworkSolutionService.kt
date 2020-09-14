package com.smart.resources.schools_app.features.homeworkSolution.data.remoteDataSource

import com.smart.resources.schools_app.core.myTypes.MyResult
import com.smart.resources.schools_app.features.homeworkSolution.data.model.HomeworkSolutionModel
import java.io.File

interface IHomeworkSolutionService {
    suspend fun addSolution(
        studentId: String,
        homeworkId: String,
        solution: String,
        attachmentImage: File?
    ): MyResult<HomeworkSolutionModel>

    suspend fun getSolution(
        homeworkId: String,
    ): MyResult<List<HomeworkSolutionModel>>
}