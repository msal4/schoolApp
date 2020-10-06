package com.smart.resources.schools_app.features.onlineExam.data.local.dataSource

import androidx.room.*
import com.smart.resources.schools_app.core.appDatabase.BaseDao
import com.smart.resources.schools_app.features.onlineExam.data.local.model.LocalOnlineExam
import com.smart.resources.schools_app.features.onlineExam.data.local.model.UserOnlineExamCrossRef
import com.smart.resources.schools_app.features.onlineExam.data.local.model.UserWithOnlineExams
import kotlinx.coroutines.flow.Flow

@Dao
abstract class OnlineExamsDao : BaseDao<LocalOnlineExam>() {
    @Transaction
    @Query("SELECT * FROM UserAccount where uid= :userId")
    abstract fun getUserOnlineExams(userId: String): Flow<List<UserWithOnlineExams>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertUserOnlineExamCrossRef(onlineExam: List<UserOnlineExamCrossRef>)

    @Query("DELETE FROM ${UserOnlineExamCrossRef.TABLE_NAME} WHERE uid NOT IN(:userIds) AND onlineExamId NOT IN(:examIds)")
    abstract fun deleteRemainingUserOnlineExamCrossRefs(
        userIds: List<String>,
        examIds: List<String>
    )

    @Query(
        """
        DELETE FROM ${LocalOnlineExam.TABLE_NAME}
        WHERE onlineExamId in (
        SELECT t1.onlineExamId 
        FROM ${LocalOnlineExam.TABLE_NAME} as t1
        LEFT JOIN ${UserOnlineExamCrossRef.TABLE_NAME} as t2 
        ON (t1.onlineExamId = t2.onlineExamId)
        WHERE t2.uid = null)"""
    )
    abstract fun deleteOnlineExamsWithoutRelations()

    /**
     * sync database with server
     * 1- upsert (insert or update) data in the database
     * 2- delete old userExam references not in the list
     * 3- delete any remaining OnlineExams without relations
     */
    @Transaction
    open fun syncWithDatabase(userId: String, onlineExams: List<LocalOnlineExam>) {
        // 1
        upsertUserOnlineExams(userId, onlineExams)

        // 2
        deleteRemainingUserOnlineExamCrossRefs(listOf(userId), onlineExams.map { it.onlineExamId })

        //3
        deleteOnlineExamsWithoutRelations()
    }

    /**
     * this method does the following
     *  1- upsert onlineExams: that is insert them if not exists otherwise update
     *  Note: upsert is necessary because using OnConflictStrategy.REPLACE will case row to be removed & added again
     *  which will cause other rows linked with them by cascaded foreign key to be deleted
     *
     *  2- insert UserOnlineExamCrossRef for the many to many relation links to be valid
     */
    @Transaction
    open fun upsertUserOnlineExams(
        userId: String,
        onlineExams: List<LocalOnlineExam>
    ) {
        val userExamsCrossRefs = onlineExams.map {
            UserOnlineExamCrossRef(
                uid = userId,
                onlineExamId = it.onlineExamId,
            )
        }

        upsert(onlineExams)
        insertUserOnlineExamCrossRef(userExamsCrossRefs)
    }

    @Query("DELETE FROM ${LocalOnlineExam.TABLE_NAME} WHERE onlineExamId = :examId")
    abstract fun deleteOnlineExamById(examId: String)
}