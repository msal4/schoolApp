package com.smart.resources.schools_app.features.login

import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.jakewharton.threetenabp.AndroidThreeTen
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.defaultGradient
import com.smart.resources.schools_app.core.extentions.applyGradient
import com.smart.resources.schools_app.core.extentions.showSnackBar
import com.smart.resources.schools_app.core.helpers.SharedPrefHelper
import com.smart.resources.schools_app.databinding.ActivityLoginBinding
import com.smart.resources.schools_app.features.users.UsersRepository
import com.smart.resources.schools_app.sharedUi.HomeActivity


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    companion object Factory {
        private const val EXTRA_MULTI_ACCOUNT= "extraMultiAccount"

        fun newInstance(context: Context, isMultiAccount: Boolean = false) {
            Intent(context, LoginActivity::class.java).apply {
                putExtra(EXTRA_MULTI_ACCOUNT, isMultiAccount)
                context.startActivity(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login).apply {
            binding = this
        }


        initComponents()
        selectNavigation()
        setupConstraintLayoutHeight()
        setupViewModel()
        binding.schoolName.applyGradient(*defaultGradient())
}

    private fun initComponents() {
        applicationContext.apply {
            AndroidThreeTen.init(this)
            SharedPrefHelper.init(this)
            Logger.addLogAdapter(AndroidLogAdapter())
            UsersRepository.init(this)
        }
    }


    private fun selectNavigation() {
        if (!intent.getBooleanExtra(EXTRA_MULTI_ACCOUNT, false) &&
            !SharedPrefHelper.instance.currentUserId.isNullOrBlank()) {
            HomeActivity.newInstance(this)
        }
    }


    private fun setupConstraintLayoutHeight() {
        val point = Point()
        windowManager.defaultDisplay.getSize(point)
        binding.constraintLayout.minHeight = point.y
    }


    private fun setupViewModel() {
        binding.apply {
            viewModel = ViewModelProviders
                .of(this@LoginActivity)
                .get(LoginViewModel::class.java)
                .also {
                    it.onLogin = ::onLogin
                    it.onLoginError = ::onLoginError
                }

            loginViewModel = viewModel

            lifecycleOwner = this@LoginActivity
        }
    }


    private fun onLogin() {
        HomeActivity.newInstance(this)
        finishAffinity()
    }

    private fun onLoginError(errorMsg: String) {
        binding.scrollView.showSnackBar(errorMsg)
    }


    override fun onBackPressed() {
        if (isTaskRoot) {
            binding.apply {
                logo.transitionName = ""
                schoolName.transitionName = ""
            }
        }
        super.onBackPressed()
    }
}


