package com.smart.resources.schools_app.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.transition.Transition
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.adapter.loadImageUrl
import com.smart.resources.schools_app.databinding.ActivityHomeBinding
import com.smart.resources.schools_app.util.IntentHelper
import com.smart.resources.schools_app.util.Section
import com.smart.resources.schools_app.util.SharedPrefHelper
import com.smart.resources.schools_app.util.toast


class HomeActivity : AppCompatActivity() {
    private lateinit var binding:ActivityHomeBinding

    companion object Factory{
        fun newInstance(context: Context){
            val intent= Intent(context, HomeActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_home)

        Logger.d("imageAmmar ${SharedPrefHelper.getInstance()?.imgUri}")
        SharedPrefHelper.getInstance()?.imgUri=null
        SharedPrefHelper.getInstance()?.imgUri?.let {
            loadImageUrl(binding.circleImageView,it)
        }

    }


    fun imageClick(view: View){
        ProfileActivity.newInstance(this)

    }



    fun navigate(view: View) {
        when(view.id){
            R.id.homework-> SectionActivity.newInstance(this, Section.HOMEWORK)
            R.id.exam-> SectionActivity.newInstance(this, Section.EXAM)
            R.id.library-> SectionActivity.newInstance(this, Section.LIBRARY)
            R.id.notifications-> SectionActivity.newInstance(this, Section.NOTIFICATION)
            R.id.schedule-> SectionActivity.newInstance(this, Section.SCHEDULE)
            R.id.absence-> SectionActivity.newInstance(this, Section.ABSENCE)
        }
    }
}
