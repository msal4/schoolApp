package com.smart.resources.schools_app.features.users.data.model.userInfo

import com.haytham.coder.extensions.decodeTokenBody
import com.smart.resources.schools_app.core.network.RetrofitHelper
import org.threeten.bp.LocalDateTime
import org.threeten.bp.Period

abstract class UserInfoModel(
    val name: String,
    val email: String?,
    val phone: String,
    val gender: String,
    private val dob: LocalDateTime,
    val schoolId: String,
    val schoolName: String,
    val schoolImage: String
) {

    val age: String
        get() = Period.between(
            dob.toLocalDate(),
            LocalDateTime.now().toLocalDate()
        ).years.toString() + " سنة"

    abstract val classesAsString: String?
    abstract val id: String

    val isEmailEmpty: Boolean get() = email.isNullOrBlank()

    companion object Factory {
        fun <T : UserInfoModel> fromToken(classType: Class<T>, accessToken: String): T? {
            return try {
                val body = accessToken.decodeTokenBody()
                val gson = RetrofitHelper.gson
                gson.fromJson(body, classType)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}
