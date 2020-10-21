package com.smart.resources.schools_app

import android.app.Application
import android.view.WindowManager
import com.jakewharton.threetenabp.AndroidThreeTen
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.BuildConfig
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.core.appDatabase.storages.SharedPrefHelper
import com.smart.resources.schools_app.core.network.AuthorizationInterceptor
import com.smart.resources.schools_app.features.users.data.UserRepository
import com.snakydesign.watchtower.WatchTower
import com.snakydesign.watchtower.interceptor.WebWatchTowerObserver
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
        Logger.addLogAdapter(AndroidLogAdapter())
        WatchTower.start(WebWatchTowerObserver(port = 8085))
    }

}