package com.smart.resources.schools_app.features.onlineExam.data.local.dataSource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.smart.resources.schools_app.core.appDatabase.BaseDao
import com.smart.resources.schools_app.features.onlineExam.data.local.model.LocalQuestion
import com.smart.resources.schools_app.features.onlineExam.domain.model.Question
import kotlinx.coroutines.flow.Flow

@Dao
abstract class QuestionsDao: BaseDao<LocalQuestion>() {
    @Query("SELECT * FROM ${LocalQuestion.TABLE_NAME} WHERE onlineExamId = :examId")
    abstract fun getExamQuestions(examId:String): Flow<List<LocalQuestion>>

//    @Query("DELETE FROM ${LocalQuestion.TABLE_NAME} WHERE onlineExamId = :examId")
//    abstract suspend fun removeExamQuestions(examId:String)

}