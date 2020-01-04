package com.smart.resources.schools_app.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.databinding.ActivitySectionBinding
import com.smart.resources.schools_app.ui.fragment.SectionFragment
import com.smart.resources.schools_app.util.Section


class SectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySectionBinding

    companion object Factory{
        private const val EXTRA_SECTION= "extraSection"

        fun newInstance(context: Context, section: Section){
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



    fun setCustomTitle(@StringRes title: Int){
        binding.titleText.text= getString(title)
    }

    private fun createFragment() {
        val fragment = SectionFragment
            .newInstance(supportFragmentManager, intent.getSerializableExtra(EXTRA_SECTION) as Section)
    }

}
