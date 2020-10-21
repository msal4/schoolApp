package com.smart.resources.schools_app.features.students.usecases

import com.hadiyarajesh.flower.ApiResponse
import com.smart.resources.schools_app.features.students.models.Student
import com.smart.resources.schools_app.features.students.models.StudentWithAnswerStatus

interface IGetClassStudentsUseCase {
    suspend operator fun invoke(classId:String):ApiResponse<List<Student>>
}

interface IGetStudentsWithAnswerStatus {
    suspend operator fun invoke(examId:String, classId:String):ApiResponse<List<StudentWithAnswerStatus>>
}