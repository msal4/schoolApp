package com.smart.resources.schools_app.features.users.data

import com.smart.resources.schools_app.core.appDatabase.storages.SharedPrefHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val accountsDao: AccountsDao,
    private val sharedPrefHelper: SharedPrefHelper,
) {
    private var currentUserAccount: UserAccount? = null
    fun getUsers() = accountsDao.getUsers()

    suspend fun getCurrentUserAccount(): UserAccount? {
        if (currentUserAccount == null) sharedPrefHelper.currentUserId?.let {
            currentUserAccount = accountsDao.getUserById(it)
        }

        return currentUserAccount
    }

    suspend fun getUser(): UserModel? {
        getCurrentUserAccount()?.apply {
            return if (userType == 0) {
                StudentModel.fromToken(accessToken.value)
            } else {
                TeacherModel.fromToken(accessToken.value)
            }
        }
        return null
    }


    suspend fun deleteUser(uid: String) {
        if (uid == currentUserAccount?.uid) {
            currentUserAccount = null
            sharedPrefHelper.currentUserId = null
        }

        accountsDao.deleteUserById(uid)
    }

    suspend fun deleteCurrentUser() {
        currentUserAccount?.uid?.let {
            deleteUser(it)
        }

    }

    suspend fun insertCurrentUser(currentUserAccount: UserAccount) {
        accountsDao.insert(currentUserAccount)
        setCurrentUser(currentUserAccount)
    }

    fun setCurrentUser(currentUserAccount: UserAccount) {
        this.currentUserAccount = currentUserAccount
        sharedPrefHelper.currentUserId = currentUserAccount.uid
    }

    suspend fun updateCurrentUser(imageLocation: String) {
        currentUserAccount?.uid?.let {
            accountsDao.updateImg(it, imageLocation)
            currentUserAccount?.img = imageLocation
        }
    }

    companion object {
        lateinit var instance: UserRepository
    }
}