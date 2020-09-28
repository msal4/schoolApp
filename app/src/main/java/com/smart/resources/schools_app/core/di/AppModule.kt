package com.smart.resources.schools_app.core.di

import android.content.Context
import androidx.room.Room
import com.smart.resources.schools_app.core.appDatabase.AppDatabase
import com.smart.resources.schools_app.core.appDatabase.MIGRATION_1_2
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        )
            .addMigrations(
                MIGRATION_1_2       // TODO: test migration
            )
            .allowMainThreadQueries() // TODO: could be removed
            .build()
    }


}