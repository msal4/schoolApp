package com.smart.resources.schools_app.features.users.data.model.userInfo

import com.google.gson.annotations.SerializedName
import com.smart.resources.schools_app.core.utils.CharSymbols
import com.smart.resources.schools_app.features.profile.ClassInfoModel
import org.threeten.bp.LocalDateTime

class TeacherInfoModel(
    @SerializedName("subjectName")
    val subjects: List<String>,
    val examType: List<String>,
    val responsibility: String,
    @SerializedName("classInfo")
    val classesInfo: List<ClassInfoModel>,
    name: String,
    email: String,
    phone: String,
    gender: String,
    dob: LocalDateTime,
    @SerializedName("idTeacher")
    override val id: String,
    schoolId: String,
    schoolName: String,
    schoolImage: String
) : UserInfoModel(name, email, phone, gender, dob,schoolId, schoolName, schoolImage) {

    companion object {
        fun fromToken(accessToken: String): TeacherInfoModel? {
            return fromToken(TeacherInfoModel::class.java, accessToken)
        }
    }
    override val classesAsString: String? get()= classesInfo
        .joinToString (
            separator = " ${CharSymbols.FILLED_CIRCLE} ",
            prefix = "${CharSymbols.FILLED_CIRCLE} ") {
        it.toString()
    }
    val subjectsAsString: String? get()= subjects.joinToString(
        separator = " ${CharSymbols.FILLED_CIRCLE} ",
        prefix = "${CharSymbols.FILLED_CIRCLE} ")
}