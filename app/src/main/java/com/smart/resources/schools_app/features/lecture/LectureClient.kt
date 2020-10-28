package com.smart.resources.schools_app.features.lecture

import com.smart.resources.schools_app.core.myTypes.MyResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface LectureClient {
    @GET("classLectures")
    suspend fun getClassLectures(@Query("schoolId") schoolId:String,
                                 @Query("classId") classId:String)
            : MyResult<List<LectureModel>>

    @POST("addSolution")
    suspend fun addSolution( @Query("studentId") studentId:String, @Query("homeworkId") homeworkId:String): Response<List<LectureModel>>
}