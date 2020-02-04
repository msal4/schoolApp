package com.smart.resources.schools_app.features.schools

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.util.Pair
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jakewharton.threetenabp.AndroidThreeTen
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.extentions.createGridLayout
import com.smart.resources.schools_app.core.helpers.SharedPrefHelper
import com.smart.resources.schools_app.databinding.ActivitySchoolsBinding
import com.smart.resources.schools_app.databinding.ItemSchoolBinding
import com.smart.resources.schools_app.features.login.CanLogout
import com.smart.resources.schools_app.features.login.LoginActivity
import com.smart.resources.schools_app.features.users.UsersRepository
import com.smart.resources.schools_app.sharedUi.HomeActivity

class SchoolsActivity : AppCompatActivity(), CanLogout {
    private lateinit var binding: ActivitySchoolsBinding
    private lateinit var viewModel: SchoolsViewModel
    private lateinit var adapter: SchoolsRecyclerAdapter
    private var firstRun:Boolean= true

    companion object Factory {
        private const val EXTRA_FIRST_RUN = "extraFirstRun"

        fun newInstance(context: Context) {
            Intent(context, SchoolsActivity::class.java).apply {
                putExtra(EXTRA_FIRST_RUN, false)
                context.startActivity(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firstRun= intent.getBooleanExtra(EXTRA_FIRST_RUN, true)

        if(firstRun){
        initComponents()
        selectNavigation()
        }
        setupBinding()
        setupViewModel()

        // TODO: remove below
        val temp= School("0", "المصادر الذكية", "", "smart")
        adapter = SchoolsRecyclerAdapter(listOf(temp), ::onItemClick)
        binding.schoolsPlaceHolder.recyclerView.createGridLayout(adapter)
        // TODO: remove above
    }

    private fun setupBinding() {
        DataBindingUtil.setContentView<ActivitySchoolsBinding>(this, R.layout.activity_schools)
            .apply {
                binding = this
                lifecycleOwner = this@SchoolsActivity

                searchText.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(p0: Editable?) {

                    }

                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        adapter.filter.filter(p0)
                    }
                })
            }
    }

    private fun initComponents() {
        applicationContext.apply {
            AndroidThreeTen.init(this)
            SharedPrefHelper.init(this)
            Logger.addLogAdapter(AndroidLogAdapter())
            UsersRepository.init(this)
            SchoolsRepository.init(this)
        }
    }

    private fun selectNavigation() {
        SharedPrefHelper.instance.apply {
            if (!intent.getBooleanExtra(EXTRA_FIRST_RUN, false) && !currentSchoolId.isNullOrBlank()) {
                // if no user logged yet
                if (currentUserId.isNullOrBlank()) LoginActivity.newInstance(this@SchoolsActivity)
                // if there is user already
                else HomeActivity.newInstance(this@SchoolsActivity)

                finish()
            }
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this)
            .get(SchoolsViewModel::class.java).apply {
                binding.schoolsPlaceHolder.listState = listState
                getSchools().observe(this@SchoolsActivity, Observer { onSchoolsDownload(it) })
            }
    }

    private fun onSchoolsDownload(schools: List<School>?) {
        schools?.let {
            Logger.i("schools data: $schools")
            adapter = SchoolsRecyclerAdapter(it, ::onItemClick)
            binding.schoolsPlaceHolder.recyclerView.createGridLayout(adapter)
        }
    }

    private fun onItemClick(model: School, itemBinding: ItemSchoolBinding) {
        SchoolsRepository.instance.insertCurrentSchool(model)

            itemBinding.apply {
                binding.apply {
                    backgroundImage.transitionName= getString(R.string.logo_trans)
                    schoolName.transitionName= getString(R.string.school_name_trans)
                }

                LoginActivity.newInstanceWithTrans(
                    this@SchoolsActivity,
                    Pair(backgroundImage, backgroundImage.transitionName),
                    Pair(schoolName, schoolName.transitionName)
                )
            }

        if(!firstRun) finishAfterTransition()
    }
}
