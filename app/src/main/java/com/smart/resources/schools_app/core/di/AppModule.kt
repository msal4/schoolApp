package com.smart.resources.schools_app.core.di

import android.content.Context
import androidx.room.Room
import com.smart.resources.schools_app.core.appDatabase.MIGRATION_1_2
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
            .addMigrations(
                MIGRATION_1_2       // TODO: test migration
            )
            .allowMainThreadQueries() // TODO: could be removed
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

            val e= EncryptionHelper(
                encryptionKey = encryptionKey,
                iv = initializationVector,
            )

//
//            val token= "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZFN0dWRlbnQiOjI1NDUsIm5hbWUiOiLYudmE2YoiLCJjbGFzc0luZm8iOnsiY2xhc3NJZCI6MzEsImNsYXNzTmFtZSI6Itin2YTYp9mI2YQiLCJzZWN0aW9uTmFtZSI6ItinIn0sInNjaG9vbElkIjo2LCJzY2hvb2xJbWFnZSI6Imh0dHBzOi8vZWR1Y2F0b2luLmFwcC9hdHRhY2htZW50L3BsYW5lICgyKS5wbmciLCJzY2hvb2xOYW1lIjoi2KzZhtipINin2YTZhdmG2LXZiNixICIsImFnZSI6NiwiZG9iIjoiMjAyMC0wOS0xNVQwMDowMDowMC4wMDBaIiwiZW1haWwiOiIiLCJwaG9uZSI6Iis5NjQwIiwiZ2VuZGVyIjoi2LDZg9ixIiwiaWF0IjoxNjAyNzc0NjA3LCJleHAiOjE2MDUzNjY2MDd9.cWqQvzFXmykuSRKUbDJfGfh3wg9K9tuJYj4zOIrll7I"
//            val temp= e.encryptMessage(token)
//
//            val encryptedToken= "-7,59,65,-98,70,1,58,24,-86,-87,49,-5,90,107,89,21,123,-100,127,18,50,4,-15,-49,81,-54,-42,78,13,9,122,-73,126,47,79,-50,-21,19,-78,-13,78,-23,79,41,-116,-96,-108,57,-94,-11,46,-65,106,-47,-79,-6,-87,-59,73,56,15,-9,-124,-39,-110,-90,-15,-91,-22,81,58,32,-127,-31,92,32,92,95,-18,-8,-118,47,25,119,-52,-84,108,80,83,-112,75,-33,-66,28,-62,11,7,94,-91,-88,-86,7,80,124,15,-88,71,-125,-71,18,4,-93,-100,102,-35,67,8,36,63,-23,25,-100,-79,-72,64,105,-16,-3,87,-79,-13,-29,45,-44,-9,105,-15,104,-125,-26,111,-64,49,-108,118,-85,-121,50,-7,-22,76,-92,69,112,30,-50,-72,115,-18,-92,54,81,-86,118,65,7,-10,84,-23,-54,45,89,109,-2,12,36,78,98,-29,-36,-95,59,100,-51,-12,38,-17,83,-78,-110,8,67,-14,9,-68,95,-45,-84,-114,87,-53,103,-8,-26,90,127,-36,-126,4,39,24,-119,-9,-97,-74,-84,100,-21,-73,95,-121,-18,1,-21,23,-85,-81,-50,5,-11,-44,-70,54,-13,121,27,-101,35,-108,-113,-123,29,-51,-115,-116,15,116,-5,72,6,112,-17,22,-8,33,-86,-77,116,-97,-78,-34,32,103,-56,54,-77,-79,-85,17,67,-111,-83,87,-112,-83,-7,115,-92,-102,-123,-46,-43,105,-80,27,11,26,85,14,96,19,10,124,13,-50,-24,-91,-37,-30,9,-20,7,110,124,16,122,40,125,71,41,3,-42,-105,-108,-113,64,103,106,-78,123,-2,81,-11,99,-101,-19,-32,-126,-121,116,-70,-127,90,-97,-1,30,34,39,48,100,-42,63,125,6,-26,-74,66,-73,77,66,-69,39,-79,60,61,28,-17,-117,104,-56,108,-54,41,96,-124,84,109,-14,41,-84,-95,-45,-21,-120,65,14,-44,-94,67,86,-25,-87,85,-56,68,111,-37,-18,-58,121,90,-94,-75,-112,89,-34,-119,-72,-59,86,70,-7,45,-49,-62,-43,-10,85,123,15,-24,-52,21,127,106,-94,64,-62,61,-110,-96,35,-15,-3,20,-89,39,-119,-36,-29,-1,-24,77,2,-3,54,-115,-85,20,-102,35,-47,-88,113,95,58,25,-42,28,55,60,24,110,-4,64,-44,123,-97,115,-111,-92,40,-60,-96,68,114,-51,27,74,-108,26,-18,81,-104,125,49,-80,40,-96,127,13,77,77,-112,-111,-23,-98,126,-87,108,14,40,-38,-30,62,-51,108,-11,72,-96,-108,63,94,1,-30,33,115,-117,11,-15,-71,-117,59,57,22,17,-63,-12,88,-113,3,13,-6,-48,32,-30,-105,13,-30,-48,-122,43,-36,36,-118,-105,-48,-11,-39,-115,-127,34,97,-18"
//            val mg= e.decryptMessage(encryptedToken)
//            val same= mg==token

            return e
        }
    }
}