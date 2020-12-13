package com.smart.resources.schools_app.features.fees

import com.smart.resources.schools_app.core.myTypes.MyResult
import retrofit2.http.GET


interface FeesClient{
    @GET("https://fees.srit-school.com/api/getStudentFees")
    suspend fun fetchFees(): MyResult<Fees>
}