package com.smart.resources.schools_app.features.profile.certificate

import com.smart.resources.schools_app.core.network.RetrofitHelper
import com.smart.resources.schools_app.core.myTypes.MyResult
import com.smart.resources.schools_app.core.myTypes.toMyResult
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class CertificateRepository: ICertificateRepository {
    override suspend fun getCertificate(studentId:String): MyResult<CertificateModel>{
        return GlobalScope.async { RetrofitHelper.certificateClient.getCertificate(studentId)}.toMyResult()
    }
}