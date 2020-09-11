package com.smart.resources.schools_app.features.lecture

import com.smart.resources.schools_app.core.myTypes.MyResult

interface ILectureRepository{
    suspend fun getLectures(schoolId:String, classId:String): MyResult<List<LectureModel>>
}