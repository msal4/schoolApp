package com.smart.resources.schools_app.features.addLecture

import com.smart.resources.schools_app.core.myTypes.MyResult
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*
import java.io.InputStream

interface AddLectureClient {
    @Multipart
    @POST("addLecture")
    suspend fun addLecture(
        @Part("subjectId") subjectId: Int,
        @Part("lecture") lecture: RequestBody,
        @Part("url") url: RequestBody,
        @Part pdfUrl: MultipartBody.Part?,
    ): MyResult<Unit>
}
