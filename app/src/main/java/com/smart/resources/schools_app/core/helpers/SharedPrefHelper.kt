package com.smart.resources.schools_app.core.helpers

import android.accounts.Account
import android.content.Context
import android.content.SharedPreferences
import com.smart.resources.schools_app.core.myTypes.UserType
import com.smart.resources.schools_app.features.profile.AccountModel

class SharedPrefHelper private constructor(context: Context) {
    private val mSharedPreferences: SharedPreferences


    var currentUser: Int?
        get() = mSharedPreferences.getInt(ACCOUNT, -1)
        set(uid) {
            if (uid != null) {
                mSharedPreferences.edit().putInt(ACCOUNT, uid).apply()
            }
        }


    companion object {
        var instance: SharedPrefHelper? = null
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