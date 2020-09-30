package com.smart.resources.schools_app.features.onlineExam.data.localDataSource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.smart.resources.schools_app.features.onlineExam.data.model.LocalQuestion
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionsDao {
    @Query("SELECT * FROM ${LocalQuestion.TABLE_NAME} WHERE onlineExamId = :examId")
    fun getExamQuestions(examId:String): Flow<List<LocalQuestion>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg question: LocalQuestion)

    @Query("DELETE FROM ${LocalQuestion.TABLE_NAME} WHERE onlineExamId = :examId")
    suspend fun removeExamQuestions(examId:String)

}