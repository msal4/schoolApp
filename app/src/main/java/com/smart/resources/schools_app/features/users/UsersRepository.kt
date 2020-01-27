package com.smart.resources.schools_app.features.users

import android.content.Context
import com.smart.resources.schools_app.core.helpers.SharedPrefHelper
import com.smart.resources.schools_app.core.AppDatabase
import com.smart.resources.schools_app.features.profile.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class UsersRepository(context: Context) {
    private var userDao: UserDao? = AppDatabase.getAppDatabase(context)?.userDao()
    private val sharedPref= SharedPrefHelper.instance

    private var currentUser: User? = null

    suspend fun getUsers()= userDao?.getUsers()

    fun getCurrentUser(): User? {
        if(currentUser == null) sharedPref.currentUserId?.let {
            currentUser = userDao?.getUserById(it)
        }

        return currentUser
    }


    fun deleteUser(uid: String){
        CoroutineScope(IO).launch {
            userDao?.deleteUserById(uid)
        }
    }

    fun deleteCurrentUser(){
        currentUser?.uid?.let { deleteUser(it) }
    }

    fun insertCurrentUser(currentUser: User) {
        CoroutineScope(IO).launch {
             userDao?.insert(currentUser)
        }
        setCurrentUser(currentUser)
    }

    fun setCurrentUser(currentUser: User) {
        this.currentUser = currentUser
        sharedPref.currentUserId = currentUser.uid
    }

    fun updateCurrentUser(imageLocation: String){
        CoroutineScope(IO).launch {
            currentUser?.uid?.let {
                userDao?.updateImg(it, imageLocation)
                currentUser?.img= imageLocation
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