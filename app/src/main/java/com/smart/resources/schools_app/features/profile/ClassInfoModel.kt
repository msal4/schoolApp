package com.smart.resources.schools_app.features.profile

data class ClassInfoModel(
    val classId: Int,
    val className: String,
    val sectionName: String
){
    val getClassSection get() = "$className - $sectionName"
    override fun toString(): String = getClassSection

}