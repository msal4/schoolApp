package com.smart.resources.schools_app.features.students

import com.hadiyarajesh.flower.ApiResponse
import com.smart.resources.schools_app.core.myTypes.MyResult
import com.smart.resources.schools_app.features.students.models.Student
import com.smart.resources.schools_app.features.students.models.StudentWithAnswerStatus
import com.smart.resources.schools_app.features.students.models.StudentWithMark
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StudentClient {
    companion object{
        private const val CLASS_ID_PATH= "id"
    }

    @GET("studentsByClass/{$CLASS_ID_PATH}")
    suspend fun getStudentsMarksByClass(@Path(CLASS_ID_PATH) classId: String): MyResult<List<StudentWithMark>>

    @GET("studentsByClass/{$CLASS_ID_PATH}")
    fun getStudentsByClassId(@Path(CLASS_ID_PATH) classId: String): Flow<ApiResponse<List<Student>>>


    @GET("examStudentAnswer")
    fun getStudentsWithAnswerStatus(
        @Query("onlineExamId") examId: String,
        @Query("classId") classId: String,
    ): Flow<ApiResponse<List<StudentWithAnswerStatus>>>
}