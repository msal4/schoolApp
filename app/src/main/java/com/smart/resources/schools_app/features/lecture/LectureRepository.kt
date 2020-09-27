package com.smart.resources.schools_app.features.lecture

import com.smart.resources.schools_app.core.utils.RetrofitHelper
import com.smart.resources.schools_app.core.myTypes.MyResult
import com.smart.resources.schools_app.core.myTypes.toMyResult
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class LectureRepository : ILectureRepository {

    override suspend fun getLectures(schoolId:String, classId:String): MyResult<List<LectureModel>> {
        return GlobalScope.async { RetrofitHelper.lectureClient.getClassLectures(schoolId, classId) }.toMyResult()
//        return Success(listOf(
//            LectureModel(
//            id= "0",
//            title = "العنوان",
//            subtitle = " كذا وكذا وكذاكذا وكذا وكذاكذا وكذا وكذاكذا وكذا وكذاكذا وكذا وكذاكذا وكذا وكذاكذا وكذا وكذاكذا وكذا وكذاكذا وكذا وكذاكذا وكذا وكذاكذا وكذا وكذاكذا وكذا وكذاكذا وكذا وكذاكذا وكذا وك",
//            date = LocalDateTime.now(),
//            url = "www.youtube.com"
//        )))
    }

}