package com.smart.resources.schools_app.features.profile

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
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
import com.smart.resources.schools_app.core.myTypes.UserType
import com.smart.resources.schools_app.databinding.ActivityProfileBinding
import com.smart.resources.schools_app.features.login.CanLogout


class ProfileActivity : AppCompatActivity(),
    CanLogout {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_profile)

        binding.apply {
            val personModel= getPersonModel()
            itemModel= personModel
            if( personModel is TeacherInfoModel){
                teacherModel= personModel
            }

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

        if(IntentHelper.GET_IMAGE_REQUEST==requestCode && resultCode== Activity.RESULT_OK && data != null){
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
        IntentHelper.selectImage(this)
    }
/*
    override fun onCreateDialog(id: Int): Dialog {
        return this?.let {
            val selectedItems = ArrayList<Int>() // Where we track the selected items
            val builder = AlertDialog.Builder(it)
            // Set the dialog title
            builder.setTitle("اختر حساب")
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setMultiChoiceItems(R.array.toppings, null,
                    DialogInterface.OnMultiChoiceClickListener { dialog, which, isChecked ->
                        if (isChecked) {
                            // If the user checked the item, add it to the selected items
                            selectedItems.add(which)
                        } else if (selectedItems.contains(which)) {
                            // Else, if the item is already in the array, remove it
                            selectedItems.remove(Integer.valueOf(which))
                        }
                    })
                // Set the action buttons
                .setPositiveButton(R.string.ok,
                    DialogInterface.OnClickListener { dialog, id ->
                        // User clicked OK, so save the selectedItems results somewhere
                        // or return them to the component that opened the dialog
                        ...
                    })
                .setNegativeButton(R.string.cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                        ...
                    })

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
*/
    fun selectMultiAccount(view: View) {

    }
}


