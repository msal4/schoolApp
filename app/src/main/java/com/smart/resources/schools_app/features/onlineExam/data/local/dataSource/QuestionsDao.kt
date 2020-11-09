package com.smart.resources.schools_app.features.onlineExam.data.local.dataSource

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.smart.resources.schools_app.core.appDatabase.BaseDao
import com.smart.resources.schools_app.features.onlineExam.data.local.model.LocalQuestion
import kotlinx.coroutines.flow.Flow

@Dao
abstract class QuestionsDao: BaseDao<LocalQuestion>() {
    @Query("SELECT * FROM ${LocalQuestion.TABLE_NAME} WHERE onlineExamId = :examId")
    abstract fun getExamQuestions(examId:String): Flow<List<LocalQuestion>>

    @Query("DELETE FROM ${LocalQuestion.TABLE_NAME} WHERE onlineExamId = :examId AND questionId NOT IN(:questionIds)")
    abstract suspend fun deleteExamQuestionsNotInList(examId: String, questionIds:List<String>)

    /**
     * sync database with server
     * 1- upsert (insert or update) data in the database
     * 2- delete old questions not in the list
     */
    @Transaction
    open suspend fun syncWithDatabase(examId: String, questions: List<LocalQuestion>) {
        // 1
        upsert(questions)

        // 2
        deleteExamQuestionsNotInList(examId, questions.map { it.questionId })
    }

}