package com.smart.resources.schools_app.features.profile.certificate

import com.smart.resources.schools_app.core.myTypes.MyResult
import com.smart.resources.schools_app.core.network.RetrofitHelper

class CertificateRepository: ICertificateRepository {
    override suspend fun getCertificate(studentId:String): MyResult<CertificateModel>{
        return RetrofitHelper.certificateClient.getCertificate(studentId)
    }
}