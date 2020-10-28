package com.smart.resources.schools_app.features.absence

import com.smart.resources.schools_app.core.myTypes.MyResult
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AbsenceClient {
    @GET("studentAbsence")
    suspend fun fetchStudentAbsence(): MyResult<List<StudentAbsenceModel>>

    @POST("addMultiAbsence")
    suspend fun addStudentAbsences(@Body postAbsenceModel: PostAbsenceModel): MyResult<Unit>
}