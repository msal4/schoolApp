package com.smart.resources.schools_app.features.lecture

import com.smart.resources.schools_app.core.myTypes.MyResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Path

interface LectureClient {
    @GET("lectures/{subjectId}")
    suspend fun getClassLectures(@Path("subjectId") subjectId:Int)
            : MyResult<List<LectureModel>>

    @POST("addSolution")
    suspend fun addSolution( @Query("studentId") studentId:String, @Query("homeworkId") homeworkId:String): Response<List<LectureModel>>
}