package com.smart.resources.schools_app.features.schools

import androidx.room.*
import com.smart.resources.schools_app.features.users.User

@Dao
interface SchoolDao {
    @Query("SELECT * FROM School")
    suspend fun getSchools(): List<School>

    @Query("SELECT * FROM School WHERE schoolId = :schoolId")
    fun getSchoolById(schoolId: String): School

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(school: School)
}