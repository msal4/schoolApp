package com.smart.resources.schools_app.features.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.adapters.loadImageUrl
import com.smart.resources.schools_app.core.adapters.setAccountImage
import com.smart.resources.schools_app.core.extentions.GET_IMAGE_REQUEST
import com.smart.resources.schools_app.core.extentions.getImage
import com.smart.resources.schools_app.core.extentions.selectImage
import com.smart.resources.schools_app.databinding.ActivityProfileBinding
import com.smart.resources.schools_app.features.login.CanLogout
import com.smart.resources.schools_app.features.users.UsersDialog
import com.smart.resources.schools_app.features.users.UsersRepository

class ProfileActivity : AppCompatActivity(){
    private lateinit var binding: ActivityProfileBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)

        setupItemModel()
    }

    private fun setupItemModel(){
        binding.apply {
            val personModel = getPerson()
            itemModel = personModel
            teacherModel = if (personModel is TeacherInfoModel)personModel else null

            UsersRepository.instance.getCurrentUser()?.img?.let {
                setAccountImage(
                    profileImage,
                    it
                )
            }
            setResult(Activity.RESULT_OK)
            setSupportActionBar(binding.toolbar)
        }
    }

    private fun getPerson():PersonModel?{
        UsersRepository.instance.getCurrentUser()?.apply {
            return if(userType==0){
                StudentInfoModel.fromToken(accessToken)
            }else{
                TeacherInfoModel.fromToken(accessToken)
            }
        }

        return null
    }

    companion object Factory {
        const val REQUEST_IS_PROFILE_IMAGE_UPDATED = 0

        fun newInstance(activity: Activity) {
            val intent = Intent(activity, ProfileActivity::class.java)
            activity.startActivityForResult(
                intent,
                REQUEST_IS_PROFILE_IMAGE_UPDATED
            )
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (GET_IMAGE_REQUEST == requestCode &&
            resultCode == Activity.RESULT_OK && data != null) {
            data.getImage().toString().let {
                UsersRepository.instance.updateCurrentUser(it)
                loadImageUrl(
                    binding.profileImage,
                    it
                )
            }

            setResult(Activity.RESULT_OK)
        }
    }


    fun selectImage(view: View) {
        selectImage(neededForLaterUsage = true)
    }


    fun selectMultiAccount(view: View) {
        UsersDialog.newInstance().apply {
            show(this@ProfileActivity.supportFragmentManager, "")
            setOnAccountChanged {
                setResult(Activity.RESULT_OK)
                setupItemModel()
            }
        }
    }
}


