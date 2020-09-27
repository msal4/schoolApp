package com.smart.resources.schools_app.features.onlineExam.data.localDataSource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.smart.resources.schools_app.features.onlineExam.data.model.LocalOnlineExam
import kotlinx.coroutines.flow.Flow

@Dao
interface OnlineExamsDao {
    @Query("SELECT * FROM ${LocalOnlineExam.TABLE_NAME}")
    fun getOnlineExams(): Flow<List<LocalOnlineExam>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(onlineExam: LocalOnlineExam)

//    @Query("SELECT * FROM UserAccount WHERE uid = :userId")
//    fun getUserById(userId: String): UserAccount
//
//
//    @Query("UPDATE UserAccount SET img = :newImg WHERE uid = :id")
//    suspend fun updateImg(id:String,newImg:String)
//
//    @Query("DELETE FROM UserAccount WHERE uid=:id")
//    suspend fun deleteUserById(id: String)

}