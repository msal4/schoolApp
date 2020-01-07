package com.smart.resources.schools_app.core

data class ClassInfoModel(
    val classId: String,
    val className: String,
    val sectionName: String




){
    val getClassSection get() = "$className - $sectionName"
}