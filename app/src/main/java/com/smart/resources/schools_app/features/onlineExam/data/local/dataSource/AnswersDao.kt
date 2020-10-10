package com.smart.resources.schools_app.features.onlineExam.data.local.dataSource

import androidx.room.*
import com.smart.resources.schools_app.core.appDatabase.BaseDao
import com.smart.resources.schools_app.features.onlineExam.data.local.model.LocalAnswer
import com.smart.resources.schools_app.features.onlineExam.data.local.model.LocalQuestion
import kotlinx.coroutines.flow.Flow

@Dao
abstract class AnswersDao: BaseDao<LocalAnswer>() {
    @Query("""
        SELECT * 
        FROM ${LocalAnswer.TABLE_NAME} 
        WHERE userId = :userId 
        AND questionId 
        IN(
        SELECT questionId 
        FROM ${LocalQuestion.TABLE_NAME}
        WHERE onlineExamId= :examId
        )""")
    abstract fun getUserExamAnswers(examId:String, userId:String): Flow<List<LocalAnswer>>


}