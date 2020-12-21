package com.smart.resources.schools_app.core.activity

//import com.smart.resources.schools_app.features.absence.addAbsence.AddAbsenceFragment
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ProgressBar
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.haytham.coder.extensions.hide
import com.haytham.coder.extensions.show
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.myTypes.Section
import com.smart.resources.schools_app.core.myTypes.Section.EXAM
import com.smart.resources.schools_app.databinding.ActivitySectionBinding
import com.smart.resources.schools_app.features.absence.addAbsence.AddAbsenceFragment
import com.smart.resources.schools_app.features.absence.getAbsence.AbsenceFragment
import com.smart.resources.schools_app.features.advertising.AdvertisingFragment
import com.smart.resources.schools_app.features.exam.ExamFragment
import com.smart.resources.schools_app.features.homework.getHomeworks.HomeworkFragment
import com.smart.resources.schools_app.features.lecture.LectureFragment
import com.smart.resources.schools_app.features.library.LibraryFragment
import com.smart.resources.schools_app.features.notification.NotificationFragment
import com.smart.resources.schools_app.features.onlineExam.presentation.fragments.OnlineExamsFragment
import com.smart.resources.schools_app.features.rating.RatingFragment
import com.smart.resources.schools_app.features.ratingAdd.AddRatingFragment
import com.smart.resources.schools_app.features.schedule.ScheduleFragment
import com.smart.resources.schools_app.features.users.data.repository.UserRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SectionActivity : BaseActivity() {
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

    fun hideToolbar(){
        binding.toolbar.hide()
    }

    fun showToolbar(){
        binding.toolbar.show()
    }


    private fun createFragment() {
        lifecycleScope.launch {
            val isStudent= UserRepository.instance.getCurrentAccount()?.userType == 0

            supportFragmentManager.apply {
                when(intent.getSerializableExtra(EXTRA_SECTION) as Section){
                    EXAM -> ExamFragment.newInstance(this)
                    Section.HOMEWORK -> HomeworkFragment.newInstance(this)
                    Section.NOTIFICATION -> NotificationFragment.newInstance(this)
                    Section.LIBRARY -> LibraryFragment.newInstance(this)
                    Section.SCHEDULE -> ScheduleFragment.newInstance(this)
                    Section.ADVERTISING -> AdvertisingFragment.newInstance(this)
                    Section.LECTURE -> LectureFragment.newInstance(this)

                    Section.ABSENCE ->
                        if(isStudent) AbsenceFragment.newInstance(this)
                        else AddAbsenceFragment.newInstance(this)

                    Section.RATING ->
                        if(isStudent) RatingFragment.newInstance(this)
                        else AddRatingFragment.newInstance(this)
                    Section.ONLINE_EXAM -> OnlineExamsFragment.newInstance(this)
                }
            }
        }
    }

    fun getToolbarProgressBar(): ProgressBar = binding.toolbarProgressBar
}
