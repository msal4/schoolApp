package com.smart.resources.schools_app.features.users.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AccountsDao {
    @Query("SELECT * FROM UserAccount")
    suspend fun getUsers(): List<UserAccount>

    @Query("SELECT * FROM UserAccount WHERE uid = :userId")
    fun getUserById(userId: String): UserAccount

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(UserAccount: UserAccount)

    @Query("UPDATE UserAccount SET img = :newImg WHERE uid = :id")
    suspend fun updateImg(id:String,newImg:String)

    @Query("DELETE FROM UserAccount WHERE uid=:id")
    suspend fun deleteUserById(id: String)

}