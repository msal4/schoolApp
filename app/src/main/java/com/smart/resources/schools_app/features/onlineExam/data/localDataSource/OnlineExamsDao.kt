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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg onlineExam: LocalOnlineExam)

    @Query("DELETE FROM ${LocalOnlineExam.TABLE_NAME} WHERE onlineExamId = :examId")
    suspend fun remove(examId:String)

}