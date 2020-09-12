package com.smart.resources.schools_app.features.lecture

import retrofit2.Response
import retrofit2.http.*

interface LectureClient {
    @GET("classLectures")
    suspend fun getClassLectures(@Query("schoolId") schoolId:String, @Query("classId") classId:String): Response<List<LectureModel>>

    @POST("addSolution")
    suspend fun addSolution( @Query("studentId") studentId:String, @Query("homeworkId") homeworkId:String): Response<List<LectureModel>>
}