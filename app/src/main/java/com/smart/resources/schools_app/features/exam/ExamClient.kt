package com.smart.resources.schools_app.features.exam

import com.smart.resources.schools_app.core.myTypes.MyResult
import com.smart.resources.schools_app.features.students.models.SendStudentResult
import com.smart.resources.schools_app.features.students.models.StudentWithMark
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ExamClient {
    @GET("examResult")
    suspend fun fetchExams(): MyResult<List<ExamModel>>

    @GET("teacherExams")
    suspend fun fetchTeacherExams(): MyResult<List<ExamModel>>

    @POST("addExam")
    suspend fun addExam(@Body postExamModel: PostExamModel): MyResult<ExamModel>

    @POST("addMultiResult")
    suspend fun addMultiResult(@Body studentMark: SendStudentResult): MyResult<Unit>

    @GET("resultByExamId/{id}")
    suspend fun getResultsByExam(@Path("id") examId: String): MyResult<List<StudentWithMark>>



}