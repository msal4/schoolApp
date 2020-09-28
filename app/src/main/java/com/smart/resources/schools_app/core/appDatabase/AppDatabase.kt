package com.smart.resources.schools_app.core.appDatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.smart.resources.schools_app.core.typeConverters.room.*
import com.smart.resources.schools_app.features.onlineExam.data.localDataSource.OnlineExamsDao
import com.smart.resources.schools_app.features.onlineExam.data.localDataSource.QuestionsDao
import com.smart.resources.schools_app.features.onlineExam.data.model.LocalOnlineExam
import com.smart.resources.schools_app.features.onlineExam.data.model.LocalQuestion
import com.smart.resources.schools_app.features.users.data.AccountsDao
import com.smart.resources.schools_app.features.users.data.UserAccount

@Database(
    entities = [
        UserAccount::class,
        LocalOnlineExam::class,
        LocalQuestion::class,
    ],
    version = 2
)
@TypeConverters(
    value = [
    DurationToLongConverter::class,
    LocalDateTimeToTimestampConverter::class,
    OnlineExamStatusToIntegerConverter::class,
    IntegersListToStringConverter::class,
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getAccountDao(): AccountsDao
    abstract fun getOnlineExamDao(): OnlineExamsDao
    abstract fun getQuestionsDao(): QuestionsDao

    companion object {
        //        private var instance: AppDatabase? = null
        const val DATABASE_NAME = "App_Database"
//
//        fun getAppDatabase(context: Context): AppDatabase? {
//            if (instance == null) {
//                instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    AppDatabase::class.java,
//                    DATABASE_NAME
//                )
//                    .allowMainThreadQueries()
//                    .build()
//            }
//            return instance
//        }
    }
}