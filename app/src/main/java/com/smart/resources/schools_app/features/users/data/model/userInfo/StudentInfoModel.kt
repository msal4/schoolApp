package com.smart.resources.schools_app.features.users.data.model.userInfo

import com.google.gson.annotations.SerializedName
import com.smart.resources.schools_app.features.profile.ClassInfoModel
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
    schoolId: String,
    schoolName: String,
    schoolImage: String
) : UserInfoModel(name, email, phone, gender, dob,schoolId, schoolName, schoolImage) {

    override val classesAsString: String
        get() = classInfo.getClassSection

    companion object {
        fun fromToken(accessToken: String): StudentInfoModel? {
            return fromToken(StudentInfoModel::class.java, accessToken)
        }
    }
}