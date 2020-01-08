package com.smart.resources.schools_app.features.section

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.databinding.ActivitySectionBinding
import com.smart.resources.schools_app.features.notification.NotificationFragment
import com.smart.resources.schools_app.core.util.Section
import com.smart.resources.schools_app.core.util.Section.EXAM
import com.smart.resources.schools_app.features.exam.ExamFragment


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

        when(val section= intent.getSerializableExtra(EXTRA_SECTION) as Section){
            EXAM -> ExamFragment.newInstance(supportFragmentManager)
            Section.NOTIFICATION -> NotificationFragment.newInstance(supportFragmentManager)
            else -> SectionFragment.newInstance(supportFragmentManager, section)
        }
    }

}
