package com.smart.resources.schools_app.features.users.data.repository

import com.smart.resources.schools_app.core.appDatabase.storages.SharedPrefHelper
import com.smart.resources.schools_app.core.myTypes.DecryptedString
import com.smart.resources.schools_app.features.users.data.localDataSource.AccountsDao
import com.smart.resources.schools_app.features.users.data.localDataSource.UsersDao
import com.smart.resources.schools_app.features.users.data.model.Account
import com.smart.resources.schools_app.features.users.data.model.User
import com.smart.resources.schools_app.features.users.data.model.userInfo.StudentInfoModel
import com.smart.resources.schools_app.features.users.data.model.userInfo.TeacherInfoModel
import com.smart.resources.schools_app.features.users.data.model.userInfo.UserInfoModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val accountsDao: AccountsDao,
    private val usersDao: UsersDao,
    private val sharedPrefHelper: SharedPrefHelper,
) {
    private var currentAccount: Account? = null
    private var currentUser: User? = null

    fun getUsers() = accountsDao.getAccounts()
    suspend fun getCurrentUser(): User? {
        if (currentUser == null) sharedPrefHelper.currentUserId?.let {
            currentUser = usersDao.getUserById(it)
        }


        return currentUser
    }

    suspend fun insertUsers(users:List<User>){
        usersDao.insert(users)
    }

    suspend fun getCurrentAccount(): Account? {
        if (currentAccount == null) sharedPrefHelper.currentUserId?.let {
            currentAccount = accountsDao.getAccountByUserId(it)
        }

        return currentAccount
    }

    suspend fun getUserModel(): UserInfoModel? {
        getCurrentAccount()?.apply {
            return if (userType == 0) {
                StudentInfoModel.fromToken(accessToken.value)
            } else {
                TeacherInfoModel.fromToken(accessToken.value)
            }
        }
        return null
    }


    suspend fun deleteAccount(uid: String) {
        if (uid == currentAccount?.userId.toString()) {
            currentAccount = null
            sharedPrefHelper.currentUserId = null
        }

        accountsDao.deleteAccountById(uid)
    }

    suspend fun deleteCurrentAccount() {
        currentAccount?.userId?.let {
            deleteAccount(it.toString())
        }
        sharedPrefHelper.currentUserId= null
    }

    suspend fun insertCurrentAccount(
        backendUserId: String,
        accessToken: DecryptedString,
        username: String,
        userType: Int,
    ) {
        val accounts= accountsDao.getAccountByBackendUserId(backendUserId)
        val foundAccount= accounts.firstOrNull { it.userType == userType }
        if(foundAccount== null){
            val user = User(0, backendUserId = backendUserId)
            val userId= usersDao.insert(user)

            val account= Account(
                userId.toInt(),
                accessToken,
                "",
                username,
                userType,
            )
            accountsDao.insert(account)
            setCurrentAccount(account)
        }else{
            setCurrentAccount(foundAccount)
        }
    }

    fun setCurrentAccount(currentAccount: Account) {
        this.currentAccount = currentAccount
        sharedPrefHelper.currentUserId = currentAccount.userId.toString()
    }

    suspend fun updateCurrentAccount(imageLocation: String) {
        currentAccount?.userId?.let {
            accountsDao.updateImg(it.toString(), imageLocation)
            currentAccount?.img = imageLocation
        }
    }

    companion object {
        lateinit var instance: UserRepository
    }
}