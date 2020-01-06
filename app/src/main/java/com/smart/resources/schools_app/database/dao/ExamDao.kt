package com.smart.resources.schools_app.database.dao

import com.smart.resources.schools_app.database.model.ExamModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface ExamDao {
    @GET("examResult")
    suspend fun fetchExam(): Response<List<ExamModel>>
}