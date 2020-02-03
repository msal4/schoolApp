package com.smart.resources.schools_app.core

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.smart.resources.schools_app.features.schools.School
import com.smart.resources.schools_app.features.schools.SchoolDao
import com.smart.resources.schools_app.features.users.User
import com.smart.resources.schools_app.features.users.UserDao

@Database(entities = [User::class, School::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun schoolDao(): SchoolDao

    companion object {
        private var instance: AppDatabase? = null
        private const val databaseName= "App_Database"

        fun getAppDatabase(context: Context): AppDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    databaseName
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return instance
        }
    }
}