package com.smart.resources.schools_app.features.schools

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher

import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.extentions.createGridLayout
import com.smart.resources.schools_app.core.myTypes.*
import com.smart.resources.schools_app.databinding.ActivitySchoolsBinding
import com.smart.resources.schools_app.features.login.CanLogout
import com.smart.resources.schools_app.features.login.SchoolsViewModel

class SchoolsActivity : AppCompatActivity(),
    CanLogout {
    private lateinit var binding: ActivitySchoolsBinding
    private lateinit var viewModel: SchoolsViewModel
    private lateinit var adapter: SchoolsRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_schools)
        setupViewModel()
//        binding.searchid.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(p0: Editable?) {
//
//            }
//
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                adapter.filter.filter(p0)
//                //Logger.d("mySearch ${p0.toString()}")
//                binding.schools.adapter=adapter
//            }
//        })
    }


    companion object Factory {

        fun newInstance(activity: Activity) {
            val intent = Intent(activity, SchoolsActivity::class.java)
            activity.startActivity(
                intent
            )
        }
    }
    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this)
            .get(SchoolsViewModel::class.java).apply {
                getSchools().observe(this@SchoolsActivity, Observer{onSchoolsDownload(it)})
            }
    }

    private  fun onSchoolsDownload(result: MyResult<List<SchoolModel>>) {
        var errorMsg = ""

        when (result) {
            is Success -> {
                if (result.data.isNullOrEmpty())
                    Logger.d("listOfScoolsAmmar result is empty")
                else {
                    Logger.d("listOfScoolsAmmar ${result.data.size}")
                    adapter=SchoolsRecyclerAdapter(result.data)
                    binding.schoolsPlaceHolder.recyclerView.createGridLayout(adapter)
                }
            }
            Unauthorized -> this?.let { expireLogout(it) }
            is ResponseError -> Logger.d("listOfScoolsAmmar ResponseError")
            is ConnectionError -> errorMsg = getString(R.string.connection_error)
        }

    }

}
