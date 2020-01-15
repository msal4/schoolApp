package com.smart.resources.schools_app.features.exam

import com.smart.resources.schools_app.features.students.SendStudentResult
import com.smart.resources.schools_app.features.students.Student
import retrofit2.Response
import retrofit2.http.*

interface ExamDao {
    @GET("examResult")
    suspend fun fetchExams(): Response<List<ExamModel>>

    @GET("teacherExams")
    suspend fun fetchTeacherExams(): Response<List<ExamModel>>

    @POST("addExam")
    suspend fun addExam(@Body postExamModel: PostExamModel): Response<ExamModel>

    @POST("addMultiResult")
    suspend fun addMultiResult(@Body studentMark: SendStudentResult): Response<Unit>

    @GET("resultByExamId/{id}")
    suspend fun getResultsByExam(@Path("id") examId: String): Response<List<Student>>



}