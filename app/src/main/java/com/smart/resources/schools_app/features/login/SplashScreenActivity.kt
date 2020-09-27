package com.smart.resources.schools_app.features.login

import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.Pair
import androidx.databinding.DataBindingUtil
import com.jakewharton.threetenabp.AndroidThreeTen
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.extentions.hideSystemUi
import com.smart.resources.schools_app.core.helpers.SharedPrefHelper
import com.smart.resources.schools_app.databinding.ActivitySplashScreenBinding
import com.smart.resources.schools_app.features.users.UsersRepository
import com.smart.resources.schools_app.sharedUi.activity.HomeActivity

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_splash_screen)

        window.hideSystemUi()
        initComponents()
        selectNextPage()
    }

    private fun initComponents() {
        applicationContext.apply {
            AndroidThreeTen.init(this)
            SharedPrefHelper.init(this)
            Logger.addLogAdapter(AndroidLogAdapter())
            UsersRepository.init(this)
        }
    }
    private fun selectNextPage() {
        if (!SharedPrefHelper.instance.currentUserId.isNullOrBlank()) {
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
