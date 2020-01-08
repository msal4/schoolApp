package com.smart.resources.schools_app.features.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.loadImageUrl
import com.smart.resources.schools_app.core.util.*
import com.smart.resources.schools_app.databinding.ActivityProfileBinding


class ProfileActivity : AppCompatActivity(),
    CanLogout {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_profile)

        binding.apply {
            itemModel= getPersonModel()
            SharedPrefHelper.instance?.imgUri?.let {
                loadImageUrl(
                    profileImage,
                    it
                )
            }

            setSupportActionBar(binding.toolbar)
        }
    }

    private fun getPersonModel() =
        if (SharedPrefHelper.instance?.userType == UserType.STUDENT) StudentInfoModel.instance
        else TeacherInfoModel.instance

    companion object Factory{
        const val REQUEST_IS_PROFILE_IMAGE_UPDATED = 0

        fun newInstance(activity: Activity){
            val intent= Intent(activity, ProfileActivity::class.java)
            activity.startActivityForResult(intent,
                REQUEST_IS_PROFILE_IMAGE_UPDATED
            )
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(IntentHelper.GET_IMAGE_REQUEST==requestCode && resultCode== Activity.RESULT_OK){
            IntentHelper.getImage(data).toString().let {
                SharedPrefHelper.instance?.imgUri= it
                loadImageUrl(
                    binding.profileImage,
                    it
                )
            }

            setResult(Activity.RESULT_OK)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_profile, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.logoutMenuItem -> logout(this)
            else -> return super.onOptionsItemSelected(item)
        }

        return true
    }

    fun selectImage(view: View) {
            IntentHelper.selectImage(this, true)
    }
}


