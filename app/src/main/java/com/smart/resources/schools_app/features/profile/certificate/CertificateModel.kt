package com.smart.resources.schools_app.features.profile.certificate

import com.google.gson.annotations.SerializedName

data class CertificateModel (
    @SerializedName("idCertificate")
    val id:String?,
    @SerializedName("certificateUrl")
    val url:String?
)
