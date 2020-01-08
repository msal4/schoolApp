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
import com.smart.resources.schools_app.core.util.SharedPrefHelper
import com.smart.resources.schools_app.core.util.showErrorSnackbar
import com.smart.resources.schools_app.features.ContainerActivities.HomeActivity


class LoginActivity : AppCompatActivity(){
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    companion object Factory{
        fun newInstance(context: Context){
            val intent= Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_login)

        initComponents()
        if(SharedPrefHelper.instance?.accessToken != null){
            HomeActivity.newInstance(
                this
            )
        }
        setupConstraintLayoutHeight()
        setupViewModel()
    }

    private fun setupConstraintLayoutHeight() {
        val params = binding.constraintLayout.layoutParams
        val point = Point()
        windowManager.defaultDisplay.getSize(point)
        params.height = point.y
    }


    private fun initComponents() {
        AndroidThreeTen.init(this)
        SharedPrefHelper.init(this)
        Logger.addLogAdapter(AndroidLogAdapter())
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
            lifecycleOwner= this@LoginActivity
        }
    }

    private fun onLogin() {
        HomeActivity.newInstance(
            this
        )
    }

    private fun onLoginError(errorMsg: String) {
        binding.scrollView.showErrorSnackbar(errorMsg)
    }

}


