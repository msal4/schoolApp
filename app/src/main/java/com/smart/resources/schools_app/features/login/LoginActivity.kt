package com.smart.resources.schools_app.features.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.databinding.DataBindingUtil
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.activity.BaseActivity
import com.smart.resources.schools_app.core.activity.HomeActivity
import com.smart.resources.schools_app.core.appDatabase.storages.SharedPrefHelper
import com.smart.resources.schools_app.core.extentions.applyGradient
import com.smart.resources.schools_app.core.extentions.showSnackBar
import com.smart.resources.schools_app.core.utils.defaultGradient
import com.smart.resources.schools_app.databinding.ActivityLoginBinding
import com.smart.resources.schools_app.features.onBoarding.OnBoardingActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var sharedPrefHelper: SharedPrefHelper

    companion object Factory {
        private const val EXTRA_MULTI_ACCOUNT = "extraMultiAccount"

        fun newInstance(context: Context, isMultiAccount: Boolean = false) {
            Intent(context, LoginActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                putExtra(EXTRA_MULTI_ACCOUNT, isMultiAccount)
                context.startActivity(this)
            }
        }

        fun newInstanceWithTrans(activity: Activity, vararg pairs: Pair<View, String>) {
            Intent(activity, LoginActivity::class.java).apply {
                val ao = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, *pairs)
                activity.startActivity(this, ao.toBundle())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login).apply {
            binding = this

        }

        selectNavigation()
        setupConstraintLayoutHeight()
        setupViewModel()
        binding.schoolName.applyGradient(*defaultGradient())
    }

    private fun selectNavigation() {
        if (!intent.getBooleanExtra(EXTRA_MULTI_ACCOUNT, false) &&
            !sharedPrefHelper.currentUserId.isNullOrBlank()
        ) {
            HomeActivity.newInstance(this)
        }
    }

    override fun onEnterAnimationComplete() {
        super.onEnterAnimationComplete()
        binding.logo.transitionName = ""
    }

    private fun setupConstraintLayoutHeight() {
        val point = Point()
        windowManager.defaultDisplay.getSize(point)
        binding.constraintLayout.minHeight = point.y
    }

    private fun setupViewModel() {
        binding.apply {
            loginViewModel = viewModel
                .also {
                    it.onLogin = ::onLogin
                    it.onFirstLogin = ::onFirstLogin
                    it.onLoginError = ::onLoginError
                }

            lifecycleOwner = this@LoginActivity
        }
    }

    private fun onLogin() {
        HomeActivity.newInstance(this)
        finishAffinity()
    }

    private fun onFirstLogin() {
        OnBoardingActivity.newInstance(this)
    }

    private fun onLoginError(errorMsg: String) {
        binding.scrollView.showSnackBar(errorMsg)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}


