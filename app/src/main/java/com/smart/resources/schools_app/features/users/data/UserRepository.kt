package com.smart.resources.schools_app.features.users.data

import com.smart.resources.schools_app.core.utils.SharedPrefHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject


class UserRepository @Inject constructor(
    private val accountsDao: AccountsDao,
    private val sharedPrefHelper: SharedPrefHelper,
) {
    private var currentUserAccount: UserAccount? = null

    suspend fun getUsers() = accountsDao.getUsers()

    fun getCurrentUserAccount(): UserAccount? {
        if (currentUserAccount == null) sharedPrefHelper.currentUserId?.let {
            currentUserAccount = accountsDao.getUserById(it)
        }

        return currentUserAccount
    }

    fun getUser(): UserModel? {
        getCurrentUserAccount()?.apply {
            return if (userType == 0) {
                StudentModel.fromToken(accessToken)
            } else {
                TeacherModel.fromToken(accessToken)
            }
        }
        return null
    }


    fun deleteUser(uid: String) {
        CoroutineScope(IO).launch {
            accountsDao.deleteUserById(uid)
        }
    }

    fun deleteCurrentUser() {
        currentUserAccount?.uid?.let { deleteUser(it) }
    }

    fun insertCurrentUser(currentUserAccount: UserAccount) {
        CoroutineScope(IO).launch {
            accountsDao.insert(currentUserAccount)
        }
        setCurrentUser(currentUserAccount)
    }

    fun setCurrentUser(currentUserAccount: UserAccount) {
        this.currentUserAccount = currentUserAccount
        sharedPrefHelper.currentUserId = currentUserAccount.uid
    }

    fun updateCurrentUser(imageLocation: String) {
        CoroutineScope(IO).launch {
            currentUserAccount?.uid?.let {
                accountsDao.updateImg(it, imageLocation)
                currentUserAccount?.img = imageLocation
            }
        }
    }

    companion object {
        lateinit var instance: UserRepository
    }
}