package com.smart.resources.schools_app.features.students.usecases

import com.hadiyarajesh.flower.ApiResponse
import com.smart.resources.schools_app.core.network.RetrofitHelper
import com.smart.resources.schools_app.features.students.models.Student
import com.smart.resources.schools_app.features.students.models.StudentWithAnswerStatus
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetStudentsWithAnswerStatusUseCase @Inject constructor(): IGetStudentsWithAnswerStatus {
    override suspend operator fun invoke(examId:String, classId:String): ApiResponse<List<StudentWithAnswerStatus>> {
        return RetrofitHelper.studentClient.getStudentsWithAnswerStatus(examId, classId).first()
    }
}