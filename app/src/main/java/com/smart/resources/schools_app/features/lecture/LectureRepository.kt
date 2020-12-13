package com.smart.resources.schools_app.features.lecture

import com.smart.resources.schools_app.core.myTypes.MyResult
import com.smart.resources.schools_app.core.network.RetrofitHelper

class LectureRepository : ILectureRepository {

    override suspend fun getLectures(subjectId:Int): MyResult<List<LectureModel>> {
        return RetrofitHelper.lectureClient.getClassLectures(subjectId)
    }

}