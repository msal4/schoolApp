package com.smart.resources.schools_app.core.helpers

import android.content.Context
import android.content.SharedPreferences
import java.lang.ClassCastException

class SharedPrefHelper private constructor(context: Context) {
    private val mSharedPreferences: SharedPreferences


    var currentUserId: String?
        get() = try{
            mSharedPreferences.getString(USER_ID, "")
        }catch (e: ClassCastException){
            // in case of value with same key is stored but with different type like INT-> this happens in older versions of app
            ""
        }
        set(uid) {
                mSharedPreferences.edit().putString(USER_ID, uid).apply()
        }

    var currentSchoolId:String?
        get() = mSharedPreferences.getString(SCHOOL_ID, "")
    set(sid){
        mSharedPreferences.edit().putString(SCHOOL_ID, sid).apply()
    }


    companion object {
        lateinit var instance: SharedPrefHelper
        private const val PREF_NAME = "mySettingsPref"
        private const val USER_ID = "userID"
        private const val SCHOOL_ID= "schoolId"


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