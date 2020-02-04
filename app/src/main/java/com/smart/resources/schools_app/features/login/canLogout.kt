package com.smart.resources.schools_app.features.login

import android.content.Context
import android.widget.Toast
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.helpers.SharedPrefHelper
import com.smart.resources.schools_app.features.users.UsersRepository


interface CanLogout{
    fun logout(context: Context){
        SharedPrefHelper.instance.currentUserId= null
        LoginActivity.newInstance(context, true)
    }

    fun expireLogout(context: Context){
        Toast.makeText(context, context.getString(R.string.account_expire), Toast.LENGTH_LONG).show()
        UsersRepository.instance.deleteCurrentUser()
        logout(context)
    }
}