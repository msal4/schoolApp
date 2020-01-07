package com.smart.resources.schools_app.features.homePage

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.loadImageUrl
import com.smart.resources.schools_app.databinding.ActivityHomeBinding
import com.smart.resources.schools_app.features.profile.ProfileActivity
import com.smart.resources.schools_app.features.section.SectionActivity
import com.smart.resources.schools_app.core.util.Section
import com.smart.resources.schools_app.core.util.SharedPrefHelper
import com.smart.resources.schools_app.core.util.toast
import java.net.URI


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
        binding.userType= SharedPrefHelper.instance?.userType

        loadProfileImage()
    }

    private fun loadProfileImage() {
        SharedPrefHelper.instance?.imgUri?.let {
            loadImageUrl(
                binding.profileImage,
                URI.create(it).toString()
            )
        }
    }

    fun imageClick(view: View){
        ProfileActivity.newInstance(
            this
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            ProfileActivity.REQUEST_IS_PROFILE_IMAGE_UPDATED -> {
                if(resultCode == Activity.RESULT_OK){
                    this.toast("result ok")
                    loadProfileImage()
                }
            }
        }
    }

    fun navigate(view: View) {
        when(view.id){
            R.id.homework-> SectionActivity.newInstance(this, Section.HOMEWORK)
            R.id.exam-> SectionActivity.newInstance(this, Section.EXAM)
            R.id.library-> SectionActivity.newInstance(this, Section.LIBRARY)
            R.id.notifications-> SectionActivity.newInstance(this, null)
            R.id.schedule-> SectionActivity.newInstance(this, Section.SCHEDULE)
            R.id.absence-> SectionActivity.newInstance(this, Section.ABSENCE)
            R.id.rating2-> SectionActivity.newInstance(this, Section.RATE)
            R.id.adver-> SectionActivity.newInstance(this, Section.ADVERTISING)


        }
    }
}