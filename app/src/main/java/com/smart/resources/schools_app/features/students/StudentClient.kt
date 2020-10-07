package com.smart.resources.schools_app.features.students

import com.hadiyarajesh.flower.ApiResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface StudentClient {
    companion object{
        private const val CLASS_ID_PATH= "id"
    }

    @GET("studentsByClass/{$CLASS_ID_PATH}")
    suspend fun getStudentsMarksByClass(@Path(CLASS_ID_PATH) classId: String): Response<List<StudentWithMark>>

    @GET("studentsByClass/{$CLASS_ID_PATH}")
    fun getStudentsByClassId(@Path(CLASS_ID_PATH) classId: String): Flow<ApiResponse<List<Student>>>
}