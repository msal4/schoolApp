package com.smart.resources.schools_app.features.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.haytham.coder.extensions.GET_IMAGE_REQUEST
import com.haytham.coder.extensions.getImage
import com.haytham.coder.extensions.openImagePickerApp
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.activity.BaseActivity
import com.smart.resources.schools_app.core.bindingAdapters.loadImageUrl
import com.smart.resources.schools_app.core.bindingAdapters.setAccountImage
import com.smart.resources.schools_app.databinding.ActivityProfileBinding
import com.smart.resources.schools_app.features.imageViewer.ImageViewerActivity
import com.smart.resources.schools_app.features.users.data.TeacherModel
import com.smart.resources.schools_app.features.users.data.UserRepository
import com.smart.resources.schools_app.features.users.presentation.AccountsDialog

class ProfileActivity : BaseActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var viewModel: ProfileViewModel

    companion object Factory {
        const val REQUEST_IS_PROFILE_IMAGE_UPDATED = 0

        fun newInstance(activity: Activity) {
            val intent = Intent(activity, ProfileActivity::class.java)
            activity.startActivityForResult(
                intent,
                REQUEST_IS_PROFILE_IMAGE_UPDATED
            )
        }

        fun newInstanceWithTrans(activity: Activity, vararg pairs: Pair<View, String>) {
            Intent(activity, ProfileActivity::class.java).apply {
                val ao =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(activity, *pairs)
                activity.startActivityForResult(this, REQUEST_IS_PROFILE_IMAGE_UPDATED, ao.toBundle())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)

        setupViewModel()
        setupItemModel()
    }

    private fun setupItemModel() {
        binding.apply {
            lifecycleOwner = this@ProfileActivity
            val personModel = getPerson()
            itemModel = personModel
            teacherModel = if (personModel is TeacherModel) personModel else null

            UserRepository.instance.getCurrentUserAccount()?.img?.let {
                setAccountImage(
                    profileImage,
                    it
                )
            }
            setResult(Activity.RESULT_OK)

            certificateBar.setOnClickListener(::openCertificateImage)
            model= viewModel
            executePendingBindings()
        }
    }

    private fun openCertificateImage(view:View){
        viewModel.certificateModel?.url?.let { ImageViewerActivity.newInstance(this,null, it) }
    }


    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this)
            .get(ProfileViewModel::class.java)
    }

    private fun getPerson()= UserRepository.instance.getUser()




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (GET_IMAGE_REQUEST == requestCode &&
            resultCode == Activity.RESULT_OK && data != null
        ) {
            data.getImage().toString().let {
                UserRepository.instance.updateCurrentUser(it)
                loadImageUrl(
                    binding.profileImage,
                    it
                )
            }
            setResult(Activity.RESULT_OK)
        }
    }

    fun selectImage(view: View) {
        openImagePickerApp(neededForLaterUsage = true)
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



