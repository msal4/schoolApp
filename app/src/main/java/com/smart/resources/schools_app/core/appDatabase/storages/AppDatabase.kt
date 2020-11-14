package com.smart.resources.schools_app.core.appDatabase.storages

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.smart.resources.schools_app.core.typeConverters.room.*
import com.smart.resources.schools_app.core.utils.EncryptionHelper
import com.smart.resources.schools_app.features.onlineExam.data.local.dataSource.AnswersDao
import com.smart.resources.schools_app.features.onlineExam.data.local.dataSource.OnlineExamsDao
import com.smart.resources.schools_app.features.onlineExam.data.local.dataSource.QuestionsDao
import com.smart.resources.schools_app.features.onlineExam.data.local.model.LocalAnswer
import com.smart.resources.schools_app.features.onlineExam.data.local.model.LocalOnlineExam
import com.smart.resources.schools_app.features.onlineExam.data.local.model.LocalQuestion
import com.smart.resources.schools_app.features.onlineExam.data.local.model.UserOnlineExamCrossRef
import com.smart.resources.schools_app.features.users.data.localDataSource.AccountsDao
import com.smart.resources.schools_app.features.users.data.localDataSource.UsersDao
import com.smart.resources.schools_app.features.users.data.model.Account
import com.smart.resources.schools_app.features.users.data.model.User

@Database(
    entities = [
        User::class,
        Account::class,
        LocalOnlineExam::class,
        UserOnlineExamCrossRef::class,
        LocalQuestion::class,
        LocalAnswer::class,
    ],
    version = 2
)
@TypeConverters(
    value = [
        DurationToLongConverter::class,
        LocalDateTimeToTimestampConverter::class,
        OnlineExamStatusToIntegerConverter::class,
        QuestionTypeToIntegerConverter::class,
        StringListToStringConverter::class,
        DecryptedStringToStringConverter::class,
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getAccountDao(): AccountsDao
    abstract fun getUsersDao(): UsersDao
    abstract fun getOnlineExamDao(): OnlineExamsDao
    abstract fun getQuestionsDao(): QuestionsDao
    abstract fun getAnswersDao(): AnswersDao

    companion object {
        const val DATABASE_NAME = "App_Database"

        // converters Dependencies
        lateinit var encryptionHelper: EncryptionHelper
    }
}