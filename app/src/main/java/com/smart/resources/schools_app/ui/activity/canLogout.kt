package com.smart.resources.schools_app.ui.activity

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.util.SharedPrefHelper


interface CanLogout{
    fun logout(activity: AppCompatActivity){
        SharedPrefHelper.instance?.accessToken= null
        activity.finishAffinity()
        LoginActivity.newInstance(activity)
    }

    fun expireLogout(activity: AppCompatActivity){
        Toast.makeText(activity, activity.getString(R.string.account_expire), Toast.LENGTH_LONG).show()
        logout(activity)
    }
}