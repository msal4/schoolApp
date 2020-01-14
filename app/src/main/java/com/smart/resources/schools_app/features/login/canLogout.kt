package com.smart.resources.schools_app.features.login

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.helpers.SharedPrefHelper
import com.smart.resources.schools_app.features.login.LoginActivity


interface CanLogout{
    fun logout(activity: AppCompatActivity){
        SharedPrefHelper.instance?.apply {
            accessToken= null
            imgUri= null
        }
        activity.finishAffinity()
        LoginActivity.newInstance(activity)
    }

    fun expireLogout(activity: AppCompatActivity){
        Toast.makeText(activity, activity.getString(R.string.account_expire), Toast.LENGTH_LONG).show()
        logout(activity)
    }
}