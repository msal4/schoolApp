package com.smart.resources.schools_app.features.users

import androidx.room.*
import com.smart.resources.schools_app.features.profile.User

@Dao
interface UserDao {
    @Query("SELECT * FROM User")
    suspend fun getUsers(): List<User>

    @Query("SELECT * FROM user WHERE uid = :userId")
    fun getUserById(userId: String): User

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    @Query("UPDATE user SET img = :newImg WHERE uid = :id")
    suspend fun updateImg(id:String,newImg:String)

    @Query("DELETE FROM user WHERE uid=:id" )
    suspend fun deleteUserById(id: String)

}