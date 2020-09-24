package com.smart.resources.schools_app.features.profile.certificate

import com.smart.resources.schools_app.core.myTypes.MyResult

interface ICertificateRepository {
    suspend fun getCertificate(studentId:String): MyResult<CertificateModel>
}