package com.smart.resources.schools_app.core.helpers

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.smart.resources.schools_app.core.adapters.LocalDateTimeConverter
import com.smart.resources.schools_app.features.absence.AbsenceDao
import com.smart.resources.schools_app.features.advertising.AdvertisingDao
import com.smart.resources.schools_app.features.exam.ExamService
import com.smart.resources.schools_app.features.homework.HomeworkService
import com.smart.resources.schools_app.features.lecture.LectureService
import com.smart.resources.schools_app.features.library.LibraryDao
import com.smart.resources.schools_app.features.login.AccountDao
import com.smart.resources.schools_app.features.notification.NotificationsDao
import com.smart.resources.schools_app.features.profile.certificate.CertificateService
import com.smart.resources.schools_app.features.rating.RatingDao
import com.smart.resources.schools_app.features.schedule.ScheduleDao
import com.smart.resources.schools_app.features.students.StudentDao
import com.smart.resources.schools_app.features.users.UsersRepository
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.threeten.bp.LocalDateTime
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object BackendHelper {

  private  val API_BASE_URL get() =  "https://educatoin.app/api/"
    // "http://directorates.srittwo.me/api/"
    val gson: Gson = GsonBuilder()
        .registerTypeAdapter(
            LocalDateTime::class.java,
            LocalDateTimeConverter()
        )
        .create()


    private val loggingInterceptor by lazy {
         HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) }
    }
    private val mBuilder get () = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))

    private val retrofit: Retrofit
            get() =
                mBuilder
                    .client(OkHttpClient.Builder().addInterceptor(loggingInterceptor).build())
                    .build()

    private val retrofitWithAuth: Retrofit
            get() =
                with(OkHttpClient.Builder()) {
                    addInterceptor(loggingInterceptor)
                    addTokenHeader()
                    mBuilder
                        .client(build())
                        .build()
                }


    private fun OkHttpClient.Builder.addTokenHeader() {
        addInterceptor(
            Interceptor { chain ->
                val token =
                    UsersRepository.instance.getCurrentUser()?.accessToken
                val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Baerer $token")
                    .build()
                chain.proceed(newRequest)
            }
        )
    }

    val homeworkService: HomeworkService get() =  retrofitWithAuth.create(HomeworkService::class.java)
    val examService: ExamService get() = retrofitWithAuth.create(ExamService::class.java)
    val lectureService: LectureService get() = retrofitWithAuth.create(LectureService::class.java)
    val certificateService: CertificateService get() = retrofitWithAuth.create(CertificateService::class.java)
    val libraryDao: LibraryDao get() =  retrofitWithAuth.create(LibraryDao::class.java)
    val notificationDao: NotificationsDao get() =  retrofitWithAuth.create(NotificationsDao::class.java)
    val absenceDao: AbsenceDao get() = retrofitWithAuth.create(AbsenceDao::class.java)
    val advertisingDao: AdvertisingDao get() =  retrofitWithAuth.create(AdvertisingDao::class.java)
    val scheduleDao: ScheduleDao get() =  retrofitWithAuth.create(ScheduleDao::class.java)
    val ratingDao: RatingDao get() =  retrofitWithAuth.create(RatingDao::class.java)
    val studentDao: StudentDao get() =  retrofitWithAuth.create(StudentDao::class.java)
    val accountDao: AccountDao get() =  retrofit.create(AccountDao::class.java)
}