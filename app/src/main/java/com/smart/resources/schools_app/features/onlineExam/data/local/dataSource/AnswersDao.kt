package com.smart.resources.schools_app.features.onlineExam.data.local.dataSource

import androidx.room.Dao
import androidx.room.Query
import com.smart.resources.schools_app.core.appDatabase.BaseDao
import com.smart.resources.schools_app.features.onlineExam.data.local.model.LocalAnswer
import com.smart.resources.schools_app.features.onlineExam.data.local.model.LocalQuestion
import com.smart.resources.schools_app.features.onlineExam.data.local.model.QuestionWithAnswer
import kotlinx.coroutines.flow.Flow

@Dao
abstract class AnswersDao : BaseDao<LocalAnswer>() {
    @Query(
        """
        SELECT
        t1.questionId,
        t1.onlineExamId,
        t1.questionText,
        t1.questionType,
        t1.options,
        t2.userId AS answers_userId,
        t2.questionId AS answers_questionId,
        t2.answerNormal AS answers_answerNormal,
        t2.answerMultiChoice AS answers_answerMultiChoice,
        t2.answerCorrectness AS answers_answerCorrectness,
        t2.correctAnswer AS answers_correctAnswer
        FROM ${LocalQuestion.TABLE_NAME} AS t1
        LEFT OUTER JOIN ${LocalAnswer.TABLE_NAME} AS t2
        ON t1.questionId == t2.questionId
        WHERE t1.onlineExamId= :examId
        AND (
        t2.userId = :userId 
        OR t2.userId IS NULL)
        """
    )
    abstract fun getUserExamAnswers(examId: String, userId: String): Flow<List<QuestionWithAnswer>>


//    @Query("""
//        SELECT *
//        FROM ${LocalAnswer.TABLE_NAME}
//        WHERE userId = :userId
//        AND questionId
//        IN(
//        SELECT questionId
//        FROM ${LocalQuestion.TABLE_NAME}
//        WHERE onlineExamId= :examId
//        )""")
//    abstract fun getUserExamAnswers(examId:String, userId:String): Flow<List<LocalAnswer>>

}