package com.smart.resources.schools_app.features.profile

import com.smart.resources.schools_app.core.util.BackendHelper
import com.smart.resources.schools_app.core.util.SharedPrefHelper
import com.smart.resources.schools_app.core.util.decodeToken
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
                val body = decodeToken(token!!)
                val gson = BackendHelper.gson
                gson.fromJson(body, classType)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}