package com.smart.resources.schools_app.features.splashScreen

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.Pair
import androidx.databinding.DataBindingUtil
import com.haytham.coder.extensions.hideSystemUi
import com.haytham.coder.extensions.startCountDown
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.activity.HomeActivity
import com.smart.resources.schools_app.databinding.ActivitySplashScreenBinding
import com.smart.resources.schools_app.features.login.LoginActivity
import com.smart.resources.schools_app.features.onBoarding.OnBoardingActivity
import dagger.hilt.android.AndroidEntryPoint
import org.threeten.bp.Duration

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private val viewModel:SplashScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash_screen)

        hideSystemUi()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.apply {
            continueSplashScreenEvent.observe(this@SplashScreenActivity) {
                it?.getContentIfNotHandled()?.let { eventHappened ->
                    if (eventHappened) {
                        continueSplashScreenToLoginScreen()
                    }
                }
            }
            gotoHomeScreenEvent.observe(this@SplashScreenActivity) {
                it?.getContentIfNotHandled()?.let { eventHappened ->
                    if (eventHappened) {
                        gotoHomeScreen()
                    }
                }
            }
            gotoOnBoardingScreenEvent.observe(this@SplashScreenActivity) {
                it?.getContentIfNotHandled()?.let { eventHappened ->
                    if (eventHappened) {
                        goToOnBoarding()
                    }
                }
            }
        }
    }

    private fun goToOnBoarding(){
        OnBoardingActivity.newInstance(this)
    }

    private fun gotoHomeScreen(){
        HomeActivity.newInstance(this)
    }

    private fun continueSplashScreenToLoginScreen(){
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
