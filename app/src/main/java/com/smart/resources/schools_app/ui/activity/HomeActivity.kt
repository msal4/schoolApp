package com.smart.resources.schools_app.ui.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.databinding.ActivityHomeBinding
import com.smart.resources.schools_app.util.Section

class HomeActivity : AppCompatActivity() {
    private lateinit var binding:ActivityHomeBinding

    companion object Factory{
        fun newInstance(context: Context){
            val intent= Intent(context, HomeActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_home)
    }

    fun navigate(view: View) {

        when(view.id){
            R.id.homework-> SectionActivity.newInstance(this, Section.HOMEWORK)
            R.id.exam-> SectionActivity.newInstance(this, Section.EXAM)
            R.id.library-> SectionActivity.newInstance(this, Section.LIBRARY)
            R.id.notifications-> SectionActivity.newInstance(this, Section.NOTIFICATION)
            R.id.schedule-> SectionActivity.newInstance(this, Section.SCHEDULE)
            R.id.absence-> SectionActivity.newInstance(this, Section.ABSENCE)

        }
    }
}
