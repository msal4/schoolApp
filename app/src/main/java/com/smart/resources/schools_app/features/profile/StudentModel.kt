package com.smart.resources.schools_app.features.profile

import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime



class StudentModel(
    val classInfo: ClassInfoModel,
    name: String,
    email: String,
    phone: String,
    gender: String,
    dob: LocalDateTime,
    @SerializedName("idStudent")
    override val id: String,
    schoolId: String,
    schoolName: String,
    schoolImage: String
) : UserModel(name, email, phone, gender, dob,schoolId, schoolName, schoolImage) {

    override val classesAsString: String
        get() = classInfo.getClassSection

    companion object {
        fun fromToken(accessToken: String): StudentModel? {
            return fromToken(StudentModel::class.java, accessToken)
        }
    }
}