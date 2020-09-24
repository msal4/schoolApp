package com.smart.resources.schools_app.features.profile.certificate

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CertificateClient {
    @GET("certificateStudent/{id}")
    suspend fun getCertificate(@Path("id") studentId:String): Response<CertificateModel>

}