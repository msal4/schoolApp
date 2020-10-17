package com.smart.resources.schools_app.core.activity

import android.os.Debug
import android.view.WindowManager
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import com.smart.resources.schools_app.core.extentions.isAppInDebugMode
import timber.log.Timber


abstract class BaseActivity : AppCompatActivity() {

    @CallSuper
    override fun onResume() {
        super.onResume()
        if (baseContext.isAppInDebugMode) { // don't even consider it otherwise
            if (Debug.isDebuggerConnected()) {
                window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                Timber.wtf(
                    "SCREEN: Keeping screen on for debugging, detach debugger and force an onResume to turn it off."
                )
            } else {
                window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                Timber.wtf("SCREEN: Keeping screen on for debugging is now deactivated.")
            }
        }
    }
}
