package com.smart.resources.schools_app.features.homeworkSolution.data.remoteDataSource

import com.smart.resources.schools_app.core.myTypes.MyResult
import java.io.File

interface IHomeworkSolutionService {
    suspend fun addSolution(
        studentId: String,
        homeworkId: String,
        solution: String,
        attachmentImage: File?
    ): MyResult<Unit>
}