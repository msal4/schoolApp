package com.smart.resources.schools_app.features.profile

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.smart.resources.schools_app.core.helpers.BackendHelper
import com.smart.resources.schools_app.core.helpers.SharedPrefHelper
import com.smart.resources.schools_app.core.myTypes.UserType
import com.smart.resources.schools_app.core.utils.decodeToken
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


class AccountModel(
    val img: String,
    val username: String,
    val accessToken: String,
    val userType: UserType
)