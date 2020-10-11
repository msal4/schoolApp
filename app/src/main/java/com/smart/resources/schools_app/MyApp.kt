package com.smart.resources.schools_app

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.smart.resources.schools_app.core.utils.AuthorizationInterceptor
import com.smart.resources.schools_app.core.utils.SharedPrefHelper
import com.smart.resources.schools_app.features.users.data.UserRepository
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApp : Application() {
    @Inject lateinit var userRepository: UserRepository
    @Inject lateinit var sharedPrefHelper: SharedPrefHelper
    @Inject lateinit var authorizationInterceptor: AuthorizationInterceptor

    override fun onCreate() {
        super.onCreate()

        AndroidThreeTen.init(this)
        UserRepository.instance= userRepository // To not break old code
        SharedPrefHelper.instance= sharedPrefHelper // To not break old code
        AuthorizationInterceptor.instance= authorizationInterceptor  // To not break old code
        setupLogger()

    }

    private fun setupLogger() {
        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }
}