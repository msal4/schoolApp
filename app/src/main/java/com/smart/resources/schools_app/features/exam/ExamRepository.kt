package com.smart.resources.schools_app.features.exam

import com.smart.resources.schools_app.core.adapters.dateDisFormatter
import com.smart.resources.schools_app.core.helpers.BackendHelper
import com.smart.resources.schools_app.core.utils.asRequestBody
import retrofit2.Response

object ExamRepository{

    suspend fun addExam(postExamModel: PostExamModel): Response<Unit>? =
        BackendHelper.examDao.addExam(postExamModel)
}