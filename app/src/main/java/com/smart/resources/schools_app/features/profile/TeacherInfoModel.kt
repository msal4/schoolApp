package com.smart.resources.schools_app.features.profile

import com.google.gson.annotations.SerializedName
import com.smart.resources.schools_app.core.ClassInfoModel
import org.threeten.bp.LocalDateTime

class TeacherInfoModel(
    val subjects: List<String>,
    val responsibilities: List<String>,
    @SerializedName("classInfo")
    val classesInfo: List<ClassInfoModel>,
    name: String,
    email: String,
    phone: String,
    gender: String,
    dob: LocalDateTime,
    @SerializedName("idTeacher")
    override val id: String
) : PersonModel(name, email, phone, gender, dob) {


    companion object {
        var instance: TeacherInfoModel? = null
            get() {
                if (field == null) field = fromToken(TeacherInfoModel::class.java)
                return field
            }

    }


    override val classesAsString: String? get() {
             val classes= StringBuilder()
             classesInfo.map { it.getClassSection }.forEach {
                 classes.append(it).append(',')
             }

             return classes.substring(0, classes.length-1)
     }
}