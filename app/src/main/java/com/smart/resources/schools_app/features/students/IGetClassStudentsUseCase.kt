package com.smart.resources.schools_app.features.students

import com.hadiyarajesh.flower.ApiResponse

interface IGetClassStudentsUseCase {
    suspend operator fun invoke(classId:String):ApiResponse<List<Student>>
}