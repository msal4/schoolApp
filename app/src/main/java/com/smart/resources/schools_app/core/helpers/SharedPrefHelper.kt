package com.smart.resources.schools_app.core.helpers

import android.accounts.Account
import android.content.Context
import android.content.SharedPreferences
import com.smart.resources.schools_app.core.myTypes.UserType
import com.smart.resources.schools_app.features.profile.AccountModel

class SharedPrefHelper private constructor(context: Context) {
    private val mSharedPreferences: SharedPreferences


    var currentUserId: String?
        get() = mSharedPreferences.getString(ACCOUNT, "")
        set(uid) {
                mSharedPreferences.edit().putString(ACCOUNT, uid).apply()
        }


    companion object {
        lateinit var instance: SharedPrefHelper
        private const val PREF_NAME = "mySettingsPref"
        private const val ACCOUNT = "Account"


        fun init(context: Context) {
            instance =
                SharedPrefHelper(
                    context
                )
        }

    }

    init {
        mSharedPreferences = context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
    }
}