package com.smart.resources.schools_app.ui.activity
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.jakewharton.threetenabp.AndroidThreeTen
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.adapter.loadImageUrl
import com.smart.resources.schools_app.databinding.ActivityLoginBinding
import com.smart.resources.schools_app.databinding.ActivityProfileBinding
import com.smart.resources.schools_app.util.IntentHelper
import com.smart.resources.schools_app.util.SharedPrefHelper
import com.smart.resources.schools_app.util.showErrorSnackbar
import com.smart.resources.schools_app.viewModel.LoginViewModel
import com.smart.resources.schools_app.viewModel.LoginViewListener


class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_profile)
        initComponents()





        SharedPrefHelper.getInstance()?.imgUri?.let {
            loadImageUrl(binding.imageView,it)
            binding.imageView2.visibility=View.GONE
            binding.imageView.alpha= 1.0F
        }

    }

    companion object Factory{
        fun newInstance(context: Context){
            val intent= Intent(context, ProfileActivity::class.java)
            context.startActivity(intent)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(IntentHelper.GET_IMAGE_REQUEST==requestCode && resultCode== Activity.RESULT_OK){
            val uri= IntentHelper.getImage(data)
            SharedPrefHelper.getInstance()?.imgUri=uri.toString()
            loadImageUrl(binding.imageView,uri.toString())

            Logger.d("uri ${uri}")
            Logger.d("uritostring ${uri.toString()}")

            binding.imageView2.visibility=View.GONE
            binding.imageView.alpha= 1.0F
        }
    }
    private fun initComponents() {
        AndroidThreeTen.init(this)
        SharedPrefHelper.init(this)
        Logger.addLogAdapter(AndroidLogAdapter())
    }

    fun selectImage(view: View) {
        IntentHelper.selectImage(this)
    }


}


