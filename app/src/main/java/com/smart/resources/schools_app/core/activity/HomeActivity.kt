package com.smart.resources.schools_app.core.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.bindingAdapters.setAccountImage
import com.smart.resources.schools_app.core.extentions.decodeTokenBody
import com.smart.resources.schools_app.databinding.ActivityHomeBinding
import com.smart.resources.schools_app.features.profile.ProfileActivity
import com.smart.resources.schools_app.core.myTypes.Section
import com.smart.resources.schools_app.core.myTypes.UserType
import com.smart.resources.schools_app.features.users.data.UserRepository
import org.json.JSONObject
import java.lang.Exception
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

        refreshUi()
    }

    private fun setUserType() {
        binding.userType =
            if (UserRepository.instance.getCurrentUserAccount()?.userType == 0) UserType.STUDENT else UserType.TEACHER
    }

    private fun loadProfileImage() {
        UserRepository.instance.getCurrentUserAccount()?.img?.let {
            setAccountImage(
                binding.profileImage,
                URI.create(it).toString()
            )
        }
    }

    private fun setSchoolName(){
        UserRepository.instance.getCurrentUserAccount()?.apply {
            try {
                JSONObject(accessToken.decodeTokenBody()).getString("schoolName").apply {
                    binding.schoolName.text = this
                }
            }catch (e:Exception){
                binding.schoolName.text= ""
            }
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
                    refreshUi()
                }
            }
        }
    }

    private fun refreshUi() {
        setUserType()
        loadProfileImage()
        setSchoolName()
    }

    fun navigate(view: View) {
        when(view.id){
            R.id.homework-> SectionActivity.newInstance(
                this,
                Section.HOMEWORK
            )
            R.id.exam-> SectionActivity.newInstance(
                this,
                Section.EXAM
            )
            R.id.library-> SectionActivity.newInstance(
                this,
                Section.LIBRARY
            )
            R.id.notifications-> SectionActivity.newInstance(
                this,
                Section.NOTIFICATION
            )
            R.id.schedule-> SectionActivity.newInstance(
                this,
                Section.SCHEDULE
            )
            R.id.absence-> SectionActivity.newInstance(
                this,
                Section.ABSENCE
            )
            R.id.ratings-> SectionActivity.newInstance(
                this,
                Section.RATING
            )
            R.id.advertisements-> SectionActivity.newInstance(
                this,
                Section.ADVERTISING
            )

            R.id.lectures-> SectionActivity.newInstance(
                this,
                Section.LECTURE
            )
            R.id.onlineExams-> SectionActivity.newInstance(
                this,
                Section.ONLINE_EXAM
            )
        }
    }
}