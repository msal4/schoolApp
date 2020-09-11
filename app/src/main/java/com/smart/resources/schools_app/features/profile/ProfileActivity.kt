package com.smart.resources.schools_app.features.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.bindingAdapters.loadImageUrl
import com.smart.resources.schools_app.core.bindingAdapters.setAccountImage
import com.smart.resources.schools_app.core.extentions.GET_IMAGE_REQUEST
import com.smart.resources.schools_app.core.extentions.getImage
import com.smart.resources.schools_app.core.extentions.selectImage
import com.smart.resources.schools_app.databinding.ActivityProfileBinding
import com.smart.resources.schools_app.features.users.AccountsDialog
import com.smart.resources.schools_app.features.users.UsersRepository
import com.smart.resources.schools_app.sharedUi.ImageViewerActivity

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var viewModel: ProfileViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)

        setupViewModel()
    }

    private fun setupItemModel() {
        binding.apply {
            lifecycleOwner = this@ProfileActivity
            val personModel = getPerson()
            itemModel = personModel
            teacherModel = if (personModel is TeacherModel) personModel else null

            UsersRepository.instance.getCurrentUserAccount()?.img?.let {
                setAccountImage(
                    profileImage,
                    it
                )
            }
            setResult(Activity.RESULT_OK)
            setSupportActionBar(binding.toolbar)

            certificateBar.setOnClickListener(::openCertificateImage)
            model= viewModel
        }
    }

    private fun openCertificateImage(view:View){
        viewModel.certificateModel?.url?.let { ImageViewerActivity.newInstance(this,null, it) }
    }


    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this)
            .get(ProfileViewModel::class.java)
    }

    private fun getPerson()= UsersRepository.instance.getUser()

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
            resultCode == Activity.RESULT_OK && data != null
        ) {
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
        AccountsDialog.newInstance().apply {
            show(this@ProfileActivity.supportFragmentManager, "")
            setOnAccountChanged {
                setResult(Activity.RESULT_OK)
                setupItemModel()
            }
        }
    }
}



