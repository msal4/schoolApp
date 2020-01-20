package com.smart.resources.schools_app.features.profile

import androidx.room.*

@Dao
interface UserDao {
    @Query("SELECT * FROM User")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray?): List<User?>?

    @Insert
    fun insertAll(vararg users: User)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: User)

    @Query("UPDATE user SET img = :newImg WHERE uid = :id")
    fun updateImg(id:Int,newImg:String)

    @Query("DELETE FROM user WHERE uid=:id" )
    fun deleteUserById(id: Int)

}