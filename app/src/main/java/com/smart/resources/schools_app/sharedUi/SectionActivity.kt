package com.smart.resources.schools_app.sharedUi

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ProgressBar
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.helpers.SharedPrefHelper
import com.smart.resources.schools_app.databinding.ActivitySectionBinding
import com.smart.resources.schools_app.features.notification.NotificationFragment
import com.smart.resources.schools_app.core.myTypes.Section
import com.smart.resources.schools_app.core.myTypes.Section.EXAM
import com.smart.resources.schools_app.core.myTypes.UserType
import com.smart.resources.schools_app.features.absence.addAbsence.AddAbsenceFragment
import com.smart.resources.schools_app.features.absence.getAbsence.AbsenceFragment
//import com.smart.resources.schools_app.features.absence.addAbsence.AddAbsenceFragment
import com.smart.resources.schools_app.features.advertising.AdvertisingFragment
import com.smart.resources.schools_app.features.exam.ExamFragment
import com.smart.resources.schools_app.features.homework.HomeworkFragment
import com.smart.resources.schools_app.features.library.LibraryFragment
import com.smart.resources.schools_app.features.rating.RatingFragment
import com.smart.resources.schools_app.features.rating.addRarting.AddRatingFragment
import com.smart.resources.schools_app.features.schedule.ScheduleFragment


class SectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySectionBinding

    companion object Factory{
        private const val EXTRA_SECTION= "extraSection"

        fun newInstance(context: Context, section: Section?){
            val intent= Intent(context, SectionActivity::class.java)
            intent.putExtra(EXTRA_SECTION, section)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_section)
        setSupportActionBar(binding.toolbar)

        createFragment()
    }


    fun setCustomTitle(title: String){
        binding.titleText.text= title
    }

    fun setCustomTitle(@StringRes title: Int){
        setCustomTitle(getString(title))
    }

    private fun createFragment() {
        val isStudent= SharedPrefHelper.instance?.userType== UserType.STUDENT

        supportFragmentManager.apply {
            when(intent.getSerializableExtra(EXTRA_SECTION) as Section){
                EXAM -> ExamFragment.newInstance(this)
                Section.HOMEWORK -> HomeworkFragment.newInstance(this)
                Section.NOTIFICATION -> NotificationFragment.newInstance(this)
                Section.LIBRARY -> LibraryFragment.newInstance(this)
                Section.SCHEDULE -> ScheduleFragment.newInstance(this)
                Section.ADVERTISING -> AdvertisingFragment.newInstance(this)

                Section.ABSENCE ->
                    if(isStudent) AbsenceFragment.newInstance(this)
                    else AddAbsenceFragment.newInstance(this)

                Section.RATING ->
                    if(isStudent) RatingFragment.newInstance(this)
                    else AddRatingFragment.newInstance(this)
            }
        }
    }

    fun getToolbarProgressBar(): ProgressBar = binding.toolbarProgressBar
}
