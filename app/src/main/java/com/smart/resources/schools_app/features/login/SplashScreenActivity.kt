package com.smart.resources.schools_app.features.login

import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.Pair
import androidx.databinding.DataBindingUtil
import com.haytham.coder.extensions.hideSystemUi
import com.haytham.coder.extensions.startCountDown
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.activity.HomeActivity
import com.smart.resources.schools_app.core.appDatabase.storages.SharedPrefHelper
import com.smart.resources.schools_app.databinding.ActivitySplashScreenBinding
import dagger.hilt.android.AndroidEntryPoint
import org.threeten.bp.Duration
import javax.inject.Inject

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    @Inject
    lateinit var sharedPrefHelper: SharedPrefHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash_screen)

        hideSystemUi()
        selectNextPage()
    }


    private fun selectNextPage() {
        if (!sharedPrefHelper.currentUserId.isNullOrBlank()) {
            HomeActivity.newInstance(this)
        } else {
            Duration.ofMillis(300).startCountDown(
                onFinished = {
                    LoginActivity.newInstanceWithTrans(
                        this@SplashScreenActivity,
                        Pair(binding.logo, getString(R.string.logo_trans))
                    )
                }
            )
        }
    }
}
