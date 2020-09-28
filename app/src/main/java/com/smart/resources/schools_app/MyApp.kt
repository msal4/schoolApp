package com.smart.resources.schools_app

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.core.utils.SharedPrefHelper
import com.smart.resources.schools_app.features.users.data.UserRepository
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApp : Application() {
    @Inject lateinit var userRepository: UserRepository
    @Inject lateinit var sharedPrefHelper: SharedPrefHelper

    override fun onCreate() {
        super.onCreate()

        AndroidThreeTen.init(this)
        UserRepository.instance= userRepository // To not break old code
        SharedPrefHelper.instance= sharedPrefHelper // To not break old code
        Logger.addLogAdapter(AndroidLogAdapter())
    }
}