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
    override val id: String
) : PersonModel(name, email, phone, gender, dob) {


    override val classesAsString: String
        get() = classInfo.getClassSection

    companion object {
        var instance: StudentInfoModel? = null
            get() {
                if (field == null) field =
                    fromToken(StudentInfoModel::class.java)
                return field
            }
            private set
    }
}