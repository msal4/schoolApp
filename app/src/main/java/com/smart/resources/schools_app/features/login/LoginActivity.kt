package com.smart.resources.schools_app.features.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.transition.ChangeBounds
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.defaultGradient
import com.smart.resources.schools_app.core.extentions.applyGradient
import com.smart.resources.schools_app.core.extentions.showSnackBar
import com.smart.resources.schools_app.core.helpers.SharedPrefHelper
import com.smart.resources.schools_app.databinding.ActivityLoginBinding
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

        fun newInstanceWithTrans(activity: Activity, vararg pairs: Pair<View, String>) {
            Intent(activity, LoginActivity::class.java).apply {
                val ao =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(activity, *pairs)
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
            !SharedPrefHelper.instance.currentUserId.isNullOrBlank()) {
            HomeActivity.newInstance(this)
        }
    }



    override fun onEnterAnimationComplete() {
        super.onEnterAnimationComplete()
        binding.logo.transitionName= ""
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
        super.onBackPressed()
        finish()
    }
}


