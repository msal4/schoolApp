package com.smart.resources.schools_app.features.homeworkSolution.data.repository

import com.smart.resources.schools_app.core.myTypes.MyResult
import com.smart.resources.schools_app.features.homeworkSolution.data.remoteDataSource.HomeworkSolutionService
import com.smart.resources.schools_app.features.homeworkSolution.data.remoteDataSource.IHomeworkSolutionService
import com.smart.resources.schools_app.features.homeworkSolution.data.model.AddHomeworkSolutionModel
import com.smart.resources.schools_app.features.homeworkSolution.domain.repository.IHomeworkSolutionRepository
import com.smart.resources.schools_app.features.homeworkSolution.data.model.HomeworkSolutionModel

class HomeworkSolutionRepository : IHomeworkSolutionRepository {
    private val solutionService: IHomeworkSolutionService = HomeworkSolutionService()

    override suspend fun getSolution(homeworkId: String): MyResult<List<HomeworkSolutionModel>> {
        return solutionService.getSolution(homeworkId)
    }

    override suspend fun addSolution(
        studentId: String,
        homeworkId: String,
        addHomeworkSolutionModel: AddHomeworkSolutionModel
    ):MyResult<HomeworkSolutionModel> {
       return solutionService.addSolution(
            studentId,
            homeworkId,
            addHomeworkSolutionModel.solution,
            addHomeworkSolutionModel.attachmentImage,
        )
    }
}