package com.smart.resources.schools_app.features.login

import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.Pair
import androidx.databinding.DataBindingUtil
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.extentions.hideSystemUi
import com.smart.resources.schools_app.core.utils.SharedPrefHelper
import com.smart.resources.schools_app.databinding.ActivitySplashScreenBinding
import com.smart.resources.schools_app.core.activity.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySplashScreenBinding
    @Inject
    lateinit var sharedPrefHelper: SharedPrefHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_splash_screen)

        window.hideSystemUi()
        selectNextPage()
    }


    private fun selectNextPage() {
        if (!sharedPrefHelper.currentUserId.isNullOrBlank()) {
            HomeActivity.newInstance(this)
        } else {
            object : CountDownTimer(300, 400) {
                override fun onFinish() {
                    LoginActivity.newInstanceWithTrans(this@SplashScreenActivity,
                        Pair(binding.logo, getString(R.string.logo_trans))
                    )
                }

                override fun onTick(millisUntilFinished: Long) {
                }

            }.start()

        }
    }
}
