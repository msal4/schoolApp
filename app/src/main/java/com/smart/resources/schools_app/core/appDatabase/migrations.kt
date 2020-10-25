package com.smart.resources.schools_app.core.appDatabase

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2){
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(SqlUtils.CREATE_ONLINE_EXAMS_TABLE)
        database.execSQL(SqlUtils.CREATE_USER_ONLINE_EXAM_CROSS_REF_TABLE)
        database.execSQL(SqlUtils.CREATE_QUESTIONS_TABLE)
        database.execSQL(SqlUtils.CREATE_ANSWERS_TABLE)
    }
}