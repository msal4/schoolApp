package com.smart.resources.schools_app.features.profile

import android.content.Context
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.core.helpers.SharedPrefHelper

class AccountManager(context: Context) {
    private var db: AppDatabase
    private var currentUser: User? = null
    fun getCurrentUser(): User? {
        currentUser =
            db.getUserById(SharedPrefHelper.instance?.currentUser?.let { intArrayOf(it) })[0]
        Logger.d("currentuser" + currentUser.toString())
        return currentUser
    }


    fun deleteUser(uid: Int){
        db.deleteUserById(uid)
    }

    fun insertCurrentUser(currentUser: User) {
        if (db.getUserById(intArrayOf(currentUser.uid)).size == 0) {
            db.insert(currentUser)
        }
        SharedPrefHelper.instance?.currentUser =
            currentUser.uid
        Logger.d("currentuser$currentUser")
        setCurrentUser(currentUser)
    }

    fun setCurrentUser(currentUser: User?) {
        this.currentUser = currentUser
    }

    companion object {
        var instance: AccountManager? =
            null


        fun init(context: Context) {
            instance =
                AccountManager(context)
        }
    }

    init {
        db = AppDatabase.getAppDatabase(context)
    }
}