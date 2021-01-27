
package com.smart.resources.schools_app

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.onesignal.OneSignal
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.core.appDatabase.storages.SharedPrefHelper
import com.smart.resources.schools_app.core.network.AuthorizationInterceptor
import com.smart.resources.schools_app.features.users.data.repository.UserRepository
import com.snakydesign.watchtower.WatchTower
import com.snakydesign.watchtower.interceptor.WebWatchTowerObserver
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.DebugTree
import javax.inject.Inject
import org.threeten.bp.LocalDate
import java.util.*

@HiltAndroidApp
class MyApp : Application() {
    @Inject
    lateinit var userRepository: UserRepository
    @Inject
    lateinit var sharedPrefHelper: SharedPrefHelper
    @Inject
    lateinit var authorizationInterceptor: AuthorizationInterceptor

    override fun onCreate() {
        super.onCreate()
        // Instantiate a FlutterEngine.
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

        AndroidThreeTen.init(this)
        UserRepository.instance = userRepository // To not break old code
        SharedPrefHelper.instance = sharedPrefHelper // To not break old code
        AuthorizationInterceptor.instance = authorizationInterceptor  // To not break old code
        Logger.addLogAdapter(AndroidLogAdapter())
        WatchTower.start(WebWatchTowerObserver(port = 8085))

        // OneSignal Initialization
        initializeOneSignal()
    }

    private fun initializeOneSignal() {
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        OneSignal.init(this, "1056409072168", "df9bc8b6-c043-4c02-8424-9a11fe40ed19")
//        OneSignal.startInit(this)
//            .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
//            .unsubscribeWhenNotificationsAreDisabled(true)
//            .init()
    }
}