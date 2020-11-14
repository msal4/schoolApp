package com.smart.resources.schools_app.features.users.data.localDataSource

import androidx.room.Dao
import androidx.room.Query
import com.smart.resources.schools_app.core.appDatabase.BaseDao
import com.smart.resources.schools_app.features.users.data.model.User

@Dao
abstract class UsersDao : BaseDao<User>() {
    @Query("SELECT * FROM User WHERE userId = :userId")
    abstract suspend fun getUserById(userId:String): User
}