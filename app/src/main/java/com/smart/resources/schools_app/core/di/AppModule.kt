package com.smart.resources.schools_app.core.di

import android.content.Context
import androidx.room.Room
import com.smart.resources.schools_app.core.appDatabase.storages.AppDatabase
import com.smart.resources.schools_app.core.appDatabase.storages.SharedPrefHelper
import com.smart.resources.schools_app.core.utils.EncryptionHelper
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
    fun provideAppDatabase(@ApplicationContext context: Context, encryptionHelper: EncryptionHelper): AppDatabase {
        AppDatabase.encryptionHelper= encryptionHelper

        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideEncryptionHelper(sharedPrefHelper: SharedPrefHelper): EncryptionHelper {
        sharedPrefHelper.apply {
            if (firstRun) {
                encryptionKey = EncryptionHelper.generateEncryptionKey()
                initializationVector = EncryptionHelper.generateInitializationVector()
                firstRun = false
            }

            return EncryptionHelper(
                encryptionKey = encryptionKey,
                iv = initializationVector,
            )
        }
    }
}