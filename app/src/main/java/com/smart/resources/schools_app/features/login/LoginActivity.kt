package com.smart.resources.schools_app.features.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
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
import com.smart.resources.schools_app.databinding.ActivityLoginBinding
import com.smart.resources.schools_app.features.schools.SchoolsActivity
import com.smart.resources.schools_app.sharedUi.HomeActivity


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    companion object Factory {

        fun newInstance(context: Context, clearPrevActivities: Boolean = false) {
            Intent(context, LoginActivity::class.java).apply {
                if (clearPrevActivities) {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }

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

        setupConstraintLayoutHeight()
        setupViewModel()
        binding.schoolName.applyGradient(*defaultGradient())
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

    fun chooseSchool(view: View) {
        if (isTaskRoot) {
            binding.apply {
                SchoolsActivity.newInstance(this@LoginActivity)
            }
            finish()
        } else {
            onBackPressed()
        }
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


