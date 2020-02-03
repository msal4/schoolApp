package com.smart.resources.schools_app.features.schools

import android.content.Context
import com.smart.resources.schools_app.core.AppDatabase
import com.smart.resources.schools_app.core.helpers.SharedPrefHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class SchoolsRepository(context: Context) {
    private var schoolDao: SchoolDao? = AppDatabase.getAppDatabase(context)?.schoolDao()
    private val sharedPref= SharedPrefHelper.instance

    var currentSchool: School?= null
        get() =
            if(field == null) schoolDao?.getSchoolById(sharedPref.currentSchoolId.toString())
            else field
    private set

    private fun insertSchool(school: School){
        CoroutineScope(IO).launch {
            schoolDao?.insert(school)
        }
    }

    fun insertCurrentSchool(school: School){
        insertSchool(school)
        currentSchool= school
        sharedPref.currentSchoolId= school.schoolId
    }


    companion object {
        lateinit var instance: SchoolsRepository

        fun init(context: Context) {
            instance =
                SchoolsRepository(
                    context
                )
        }
    }
}