package com.smart.resources.schools_app.features.lecture

import com.smart.resources.schools_app.core.myTypes.MyResult

interface ILectureRepository{
    suspend fun getLectures(subjectId:Int): MyResult<List<LectureModel>>
}