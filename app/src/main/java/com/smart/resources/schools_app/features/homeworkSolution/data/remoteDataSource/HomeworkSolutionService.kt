package com.smart.resources.schools_app.features.homeworkSolution.data.remoteDataSource

import com.smart.resources.schools_app.core.extentions.asBodyPart
import com.smart.resources.schools_app.core.extentions.asRequestBody
import com.smart.resources.schools_app.core.utils.RetrofitHelper
import com.smart.resources.schools_app.core.myTypes.MyResult
import com.smart.resources.schools_app.core.myTypes.toMyResult
import com.smart.resources.schools_app.features.homeworkSolution.data.model.HomeworkSolutionModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.io.File

class HomeworkSolutionService : IHomeworkSolutionService {
    private val homeworkSolutionClient = RetrofitHelper.homeworkSolutionClient

    companion object {
        const val IMAGE_FILED_NAME = "solutionImage"
    }

    override suspend fun addSolution(
        studentId: String,
        homeworkId: String,
        solution: String,
        attachmentImage: File?
    ): MyResult<HomeworkSolutionModel> {
        return GlobalScope.async {
            homeworkSolutionClient.addSolution(
                studentId = studentId.asRequestBody(),
                homeworkId = homeworkId.asRequestBody(),
                solutionText = solution.asRequestBody(),
                solutionImage = attachmentImage?.asBodyPart(IMAGE_FILED_NAME)
            )
        }.toMyResult()
    }

    override suspend fun getSolution(homeworkId: String): MyResult<List<HomeworkSolutionModel>> {
        return GlobalScope.async { homeworkSolutionClient.getHomeworkSolution(homeworkId) }.toMyResult()
    }
}