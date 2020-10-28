package com.smart.resources.schools_app.features.lecture

import com.smart.resources.schools_app.core.myTypes.MyResult
import com.smart.resources.schools_app.core.network.RetrofitHelper

class LectureRepository : ILectureRepository {

    override suspend fun getLectures(schoolId:String, classId:String): MyResult<List<LectureModel>> {
        return RetrofitHelper.lectureClient.getClassLectures(schoolId, classId)
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