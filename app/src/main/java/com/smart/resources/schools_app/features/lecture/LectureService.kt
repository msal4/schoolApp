package com.smart.resources.schools_app.features.lecture

import com.smart.resources.schools_app.features.students.SendStudentResult
import com.smart.resources.schools_app.features.students.Student
import retrofit2.Response
import retrofit2.http.*

interface LectureService {
    @GET("classLectures")
    suspend fun getClassLectures(@Query("schoolId") schoolId:String, @Query("classId") classId:String): Response<List<LectureModel>>

}