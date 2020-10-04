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

    /**
     * this method does the following
     *  1- upsert onlineExams: that is insert them if not exists otherwise update
     *  Note: upsert is necessary because using OnConflictStrategy.REPLACE will case row to be removed & added again
     *  which will cause other rows linked with them by cascaded foreign key to be deleted
     *
     *  2- insert UserOnlineExamCrossRef for the many to many relation links to be valid
     */
    @Transaction
    open fun insertUserOnlineExams(userId: String, onlineExams: List<LocalOnlineExam>) {

        val userExamsCrossRef = onlineExams.map {
            UserOnlineExamCrossRef(
                uid = userId,
                onlineExamId = it.onlineExamId,
            )
        }

        upsert(onlineExams)
        insertUserOnlineExamCrossRef(userExamsCrossRef)
    }

    @Query("DELETE FROM ${LocalOnlineExam.TABLE_NAME} WHERE onlineExamId = :examId")
    abstract fun removeUserOnlineExam(examId:String)
}