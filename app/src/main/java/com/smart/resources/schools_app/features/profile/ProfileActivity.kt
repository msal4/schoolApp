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
import com.smart.resources.schools_app.core.helpers.IntentHelper
import com.smart.resources.schools_app.core.helpers.SharedPrefHelper
import com.smart.resources.schools_app.core.adapters.loadImageUrl
import com.smart.resources.schools_app.core.adapters.setAccountImage
import com.smart.resources.schools_app.databinding.ActivityProfileBinding
import com.smart.resources.schools_app.features.login.CanLogout




class ProfileActivity : AppCompatActivity(),
    CanLogout {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)

        setupItemModel()
    }


    fun setupItemModel(){
        binding.apply {
            val personModel = getPerson()
            itemModel = personModel
            if (personModel is TeacherInfoModel) {
                teacherModel = personModel
            }
            AccountManager.instance?.getCurrentUser()?.img?.let {
                setAccountImage(
                    profileImage,
                    it
                )
            }
            setResult(Activity.RESULT_OK)
            setSupportActionBar(binding.toolbar)
        }
    }

    private fun getPerson():PersonModel{
        val db=AppDatabase.getAppDatabase(this)
        val user=db.getUserById(AccountManager.instance?.getCurrentUser()?.uid?.let { intArrayOf(it) })
        var newUser:PersonModel
        if(user[0].userType==0){
            newUser= StudentInfoModel.fromToken(user[0].accessToken)!!
        }else{
            newUser= TeacherInfoModel.fromToken(user[0].accessToken)!!
        }
        return newUser
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

        if (IntentHelper.GET_IMAGE_REQUEST == requestCode && resultCode == Activity.RESULT_OK && data != null) {
            IntentHelper.getImage(data).toString().let {
                val db=AppDatabase.getAppDatabase(this)
                SharedPrefHelper.instance?.currentUser?.let { it1 -> db.update(it1,it) }
                loadImageUrl(
                    binding.profileImage,
                    it
                )
            }

            setResult(Activity.RESULT_OK)
        }
    }


    fun selectImage(view: View) {
        IntentHelper.selectImage(this,neededForLaterUsage = true)
    }

    fun select(isLogout:Boolean){


        var db = AppDatabase.getAppDatabase(this)

        var list = db.getAllUsers()


        MyDialogFragment.newInstance(list,isLogout).apply {
            show(this@ProfileActivity.supportFragmentManager, "")
            setOnFinish {
                setupItemModel()
            }
        }

    }

    fun selectMultiAccount(view: View) {

        select(false)

    }
}


