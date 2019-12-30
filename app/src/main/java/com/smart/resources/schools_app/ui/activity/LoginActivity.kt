package com.smart.resources.schools_app.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.databinding.ActivityLoginBinding
import com.smart.resources.schools_app.viewModel.LoginViewModel
import com.smart.resources.schools_app.viewModel.myInterface.LoginViewListener

class LoginActivity : AppCompatActivity(), LoginViewListener {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel:LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_login)


        setupViewModel()
    }

    private fun setupViewModel() {
        binding.apply {
            lifecycleOwner= this@LoginActivity
            viewModel = ViewModelProviders
                .of(this@LoginActivity).get(LoginViewModel::class.java)
                .apply {
                    listener = this@LoginActivity
                }
            loginViewModel = viewModel

        }
    }

    override fun loginAsTeacher() {
        HomeActivity.newInstance(this)
    }

    override fun loginAsStudent() {

    }
}


