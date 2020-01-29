package com.smart.resources.schools_app.features.profile

import com.smart.resources.schools_app.core.helpers.BackendHelper
import com.smart.resources.schools_app.core.myTypes.UserType
import com.smart.resources.schools_app.core.extentions.decodeToken
import org.threeten.bp.LocalDateTime
import org.threeten.bp.Period

abstract class PersonModel(
    val name: String, val email: String, val phone: String,
    val gender: String, private val dob: LocalDateTime
) {

    val age: String
        get() = Period.between(
            dob.toLocalDate(),
            LocalDateTime.now().toLocalDate()
        ).years.toString() + " سنة"

    abstract val classesAsString: String?
    abstract val id: String


    val isEmailEmpty:Boolean get() = email.isBlank()

    companion object Factory {
        fun <T : PersonModel> fromToken(classType: Class<T>,accessToken: String): T? {
            return try {
                val body = accessToken.decodeToken()
                val gson = BackendHelper.gson
                gson.fromJson(body, classType)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}
