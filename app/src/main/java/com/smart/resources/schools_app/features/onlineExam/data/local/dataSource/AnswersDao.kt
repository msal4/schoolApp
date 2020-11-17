package com.smart.resources.schools_app.features.onlineExam.data.local.dataSource

import androidx.room.Dao
import androidx.room.Query
import com.smart.resources.schools_app.core.appDatabase.BaseDao
import com.smart.resources.schools_app.features.onlineExam.data.local.model.LocalAnswer
import com.smart.resources.schools_app.features.onlineExam.data.local.model.LocalQuestion
import com.smart.resources.schools_app.features.onlineExam.data.local.model.QuestionWithAnswer
import com.smart.resources.schools_app.features.users.data.model.Account
import com.smart.resources.schools_app.features.users.data.model.User
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
        FROM (
        SELECT * 
        FROM ${LocalQuestion.TABLE_NAME} 
        WHERE onlineExamId = :examId) AS t1
        LEFT OUTER JOIN (
        SELECT * 
        FROM ${LocalAnswer.TABLE_NAME} AS answer
        INNER JOIN ${User.TABLE_NAME} AS user
        ON answer.userId == user.userId
        AND user.backendUserId = :backendUserId 
        LEFT OUTER JOIN ${Account.TABLE_NAME} AS account
        ON user.userId == account.userId
        AND account.userType!= 1
        ) AS t2
        ON t1.questionId == t2.questionId
        """
    )
    abstract fun getUserExamQuestionsWithAnswers(examId: String, backendUserId: String): Flow<List<QuestionWithAnswer>>


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