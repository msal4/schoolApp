package com.smart.resources.schools_app.features.profile

import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime



class StudentInfoModel(
    val classInfo: ClassInfoModel,
    name: String,
    email: String,
    phone: String,
    gender: String,
    dob: LocalDateTime,
    @SerializedName("idStudent")
    override val id: String,
    schoolName: String,
    schoolImage: String
) : PersonModel(name, email, phone, gender, dob, schoolName, schoolImage) {

    override val classesAsString: String
        get() = classInfo.getClassSection

    companion object {
        fun fromToken(accessToken: String): StudentInfoModel? {
            return fromToken(StudentInfoModel::class.java, accessToken)
        }
    }
}