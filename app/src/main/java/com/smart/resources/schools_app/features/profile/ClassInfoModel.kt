package com.smart.resources.schools_app.features.profile

import com.smart.resources.schools_app.core.extentions.toUnicodeString

data class ClassInfoModel(
    val classId: Int,
    val className: String,
    val sectionName: String
){
    val getClassSection get() = "${className.toUnicodeString()} - ${sectionName.toUnicodeString()}"
    override fun toString(): String = getClassSection
}