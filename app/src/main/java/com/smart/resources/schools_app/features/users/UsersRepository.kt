package com.smart.resources.schools_app.features.users

import android.content.Context
import com.smart.resources.schools_app.core.helpers.SharedPrefHelper
import com.smart.resources.schools_app.core.AppDatabase
import com.smart.resources.schools_app.features.profile.UserModel
import com.smart.resources.schools_app.features.profile.StudentModel
import com.smart.resources.schools_app.features.profile.TeacherModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class UsersRepository(context: Context) {
    private var accountDao: AccountDao? = AppDatabase.getAppDatabase(context)?.userDao()
    private val sharedPref= SharedPrefHelper.instance
    private var currentUserAccount: UserAccount? = null

    suspend fun getUsers()= accountDao?.getUsers()

    fun getCurrentUserAccount(): UserAccount? {
        if(currentUserAccount == null) sharedPref.currentUserId?.let {
            currentUserAccount = accountDao?.getUserById(it)
        }

        return currentUserAccount
    }

    fun getUser(): UserModel?{
        getCurrentUserAccount()?.apply {
            return if (userType == 0) {
                StudentModel.fromToken(accessToken)
            } else {
                TeacherModel.fromToken(accessToken)
            }
        }
        return null
    }



    fun deleteUser(uid: String){
        CoroutineScope(IO).launch {
            accountDao?.deleteUserById(uid)
        }
    }

    fun deleteCurrentUser(){
        currentUserAccount?.uid?.let { deleteUser(it) }
    }

    fun insertCurrentUser(currentUserAccount: UserAccount) {
        CoroutineScope(IO).launch {
             accountDao?.insert(currentUserAccount)
        }
        setCurrentUser(currentUserAccount)
    }

    fun setCurrentUser(currentUserAccount: UserAccount) {
        this.currentUserAccount = currentUserAccount
        sharedPref.currentUserId = currentUserAccount.uid
    }

    fun updateCurrentUser(imageLocation: String){
        CoroutineScope(IO).launch {
            currentUserAccount?.uid?.let {
                accountDao?.updateImg(it, imageLocation)
                currentUserAccount?.img= imageLocation
            }
        }
    }

    companion object {
        lateinit var instance: UsersRepository


        fun init(context: Context) {
            instance =
                UsersRepository(
                    context
                )
        }
    }
}