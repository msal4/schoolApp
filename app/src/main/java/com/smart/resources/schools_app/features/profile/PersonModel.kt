package com.smart.resources.schools_app.features.profile

import com.smart.resources.schools_app.core.helpers.BackendHelper
import com.smart.resources.schools_app.core.helpers.SharedPrefHelper
import com.smart.resources.schools_app.core.utils.decodeToken
import org.threeten.bp.LocalDateTime
import org.threeten.bp.Period

abstract class PersonModel( val name: String, val email: String, val phone: String,
                           val gender: String, private val dob: LocalDateTime
) {

    val age: String
        get() = Period.between(
            dob.toLocalDate(),
            LocalDateTime.now().toLocalDate()
        ).years.toString() + " سنة"

    abstract val classesAsString: String?
    abstract val id:String

    companion object Factory{
         fun <T:PersonModel>fromToken(classType:Class<T>): T? {
            return try {
                val token = SharedPrefHelper.instance?.accessToken
                val body = token?.decodeToken()
                val gson = BackendHelper.gson
                gson.fromJson(body, classType)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}