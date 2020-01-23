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
import com.smart.resources.schools_app.databinding.ActivityLoginBinding
import com.smart.resources.schools_app.core.helpers.SharedPrefHelper
import com.smart.resources.schools_app.core.utils.showSnackBar
import com.smart.resources.schools_app.features.users.UsersRepository
import com.smart.resources.schools_app.sharedUi.HomeActivity


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    companion object Factory {
        fun newInstance(context: Context) {
             Intent(context, LoginActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                 addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        initComponents()
        if (!SharedPrefHelper.instance.currentUserId.isNullOrBlank()) {
            HomeActivity.newInstance(this)
        }
        setupConstraintLayoutHeight()
        setupViewModel()
    }

    private fun setupConstraintLayoutHeight() {
        val point = Point()
        windowManager.defaultDisplay.getSize(point)
        binding.constraintLayout.minHeight = (point.y * 0.9).toInt()
    }


    private fun initComponents() {
        AndroidThreeTen.init(this)
        SharedPrefHelper.init(this)
        Logger.addLogAdapter(AndroidLogAdapter())
        UsersRepository.init(this)
    }

    private fun setupViewModel() {
        binding.apply {
            viewModel = ViewModelProviders
                .of(this@LoginActivity).get(LoginViewModel::class.java)
                .also {
                    it.onLogin = ::onLogin
                    it.onLoginError = ::onLoginError
                }
            loginViewModel = viewModel
            lifecycleOwner = this@LoginActivity
        }
    }



    private fun onLogin() {

        HomeActivity.newInstance(
            this
        )
    }

    private fun onLoginError(errorMsg: String) {
        binding.scrollView.showSnackBar(errorMsg)
    }

}


