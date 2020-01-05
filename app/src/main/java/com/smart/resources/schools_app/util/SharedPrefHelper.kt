package com.smart.resources.schools_app.util

import android.content.Context
import android.content.SharedPreferences

class SharedPrefHelper private constructor(context: Context) {

    var imgUri: String?
        get() = mSharedPreferences.getString(IMG_URI, null)
        set(accessToken) {
            mSharedPreferences.edit().putString(IMG_URI, accessToken).apply()
        }

    private val mSharedPreferences: SharedPreferences
    var accessToken: String?
        get() = mSharedPreferences.getString(ACCESS_TOKEN, null)
        set(accessToken) {
            mSharedPreferences.edit().putString(ACCESS_TOKEN, accessToken)
                .apply()
        }

    companion object {
        var instance: SharedPrefHelper? = null
        private const val PREF_NAME = "mySettingsPref"
        private const val IMG_URI = "myImage"
        private const val ACCESS_TOKEN = "ACCESS_TOKEN"

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