package com.smart.resources.schools_app.features.login

import android.app.Activity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.helpers.SharedPrefHelper
import com.smart.resources.schools_app.features.profile.AccountManager


interface CanLogout{
    fun logout(activity: AppCompatActivity){
        SharedPrefHelper.instance?.currentUser =-1
        //if(SharedPrefHelper.instance?.userType==UserType.TEACHER)
        activity.setResult(Activity.RESULT_CANCELED)
        activity.finishAffinity()
        LoginActivity.newInstance(activity)
    }

    fun expireLogout(activity: AppCompatActivity){
        Toast.makeText(activity, activity.getString(R.string.account_expire), Toast.LENGTH_LONG).show()
        logout(activity)
    }
}