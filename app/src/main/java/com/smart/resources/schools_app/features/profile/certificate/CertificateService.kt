package com.smart.resources.schools_app.features.profile.certificate

import com.smart.resources.schools_app.features.profile.certificate.CertificateModel
import retrofit2.Response
import retrofit2.http.*

interface CertificateService {
    @GET("certificateStudent/{id}")
    suspend fun getCertificate(@Path("id") studentId:String): Response<CertificateModel>

}