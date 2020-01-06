package com.smart.resources.schools_app.util

import android.content.Context
import android.content.SharedPreferences

class SharedPrefHelper private constructor(context: Context) {
    private val mSharedPreferences: SharedPreferences

    var imgUri: String?
        get() = mSharedPreferences.getString(IMG_URI, null)
        set(accessToken) {
            mSharedPreferences.edit().putString(IMG_URI, accessToken).apply()
        }

    var accessToken: String?
        get() = mSharedPreferences.getString(ACCESS_TOKEN, null)
        set(accessToken) {
            mSharedPreferences.edit().putString(ACCESS_TOKEN, accessToken)
                .apply()
        }

    var userType: UserType?
        get() {
            return  when(mSharedPreferences.getInt(USER_TYPE, -1)){
                0-> UserType.STUDENT
                1-> UserType.TEACHER
                else -> null
            }
        }
        set(userType) {
            if (userType != null) {
                mSharedPreferences.edit().putInt(USER_TYPE, userType.ordinal).apply()
            }
        }

    companion object {
        var instance: SharedPrefHelper? = null
        private const val PREF_NAME = "mySettingsPref"
        private const val IMG_URI = "myImage"
        private const val ACCESS_TOKEN = "accessToken"
        private const val USER_TYPE = "userType"

        fun init(context: Context) {
            instance = SharedPrefHelper(context)
        }

    }

    init {
        mSharedPreferences = context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
    }
}