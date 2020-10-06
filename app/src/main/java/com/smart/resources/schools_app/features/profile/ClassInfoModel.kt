package com.smart.resources.schools_app.features.profile

import com.haytham.coder.extensions.unicodeWrap


data class ClassInfoModel(
    val classId: String,
    val className: String,
    val sectionName: String
){
    val getClassSection get() = "${className.unicodeWrap()} - ${sectionName.unicodeWrap()}"
    override fun toString(): String = getClassSection
}