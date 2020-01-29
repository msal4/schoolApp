package com.smart.resources.schools_app.core.helpers

import com.google.gson.GsonBuilder
import com.smart.resources.schools_app.core.adapters.LocalDateTimeConverter
import com.smart.resources.schools_app.features.absence.AbsenceDao
import com.smart.resources.schools_app.features.advertising.AdvertisingDao
import com.smart.resources.schools_app.features.exam.ExamDao
import com.smart.resources.schools_app.features.homework.HomeworkDao
import com.smart.resources.schools_app.features.library.LibraryDao
import com.smart.resources.schools_app.features.login.AccountDao
import com.smart.resources.schools_app.features.notification.NotificationsDao
import com.smart.resources.schools_app.features.users.UsersRepository
import com.smart.resources.schools_app.features.rating.RatingDao
import com.smart.resources.schools_app.features.schedule.ScheduleDao
import com.smart.resources.schools_app.features.students.StudentDao
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.threeten.bp.LocalDateTime
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BackendHelper {
    private const val SCHOOL_NAME= "ameerp"     // TODO: change1: base url

    private const val API_BASE_URL = "http://$SCHOOL_NAME.srittwo.me/api/"
    //private const val API_BASE_URL = "https://api.androidhive.info/json/"
    val gson = GsonBuilder()
        .registerTypeAdapter(
            LocalDateTime::class.java,
            LocalDateTimeConverter()
        )
        .create()


    private val mBuilder = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))

    private val retrofit: Retrofit
            get() =
                mBuilder
                    .client(OkHttpClient.Builder().build())
                    .build()

    private val retrofitWithAuth: Retrofit
            by lazy {
                with(OkHttpClient.Builder()) {
                    addTokenHeader()
                    mBuilder
                        .client(build())
                        .build()
                }

            }

    private fun OkHttpClient.Builder.addTokenHeader() {
        addInterceptor(
            Interceptor { chain ->
                val token =
                    UsersRepository.instance.getCurrentUser()?.accessToken ?: return@Interceptor null
                val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Baerer $token")
                    .build()
                chain.proceed(newRequest)
            }
        )
    }

    val homeworkDao: HomeworkDao by lazy{retrofitWithAuth.create(HomeworkDao::class.java)}
    val examDao: ExamDao by lazy{retrofitWithAuth.create(ExamDao::class.java)}
    val libraryDao: LibraryDao by lazy{ retrofitWithAuth.create(LibraryDao::class.java)}
    val notificationDao: NotificationsDao by lazy{retrofitWithAuth.create(NotificationsDao::class.java)}
    val absenceDao: AbsenceDao by lazy{retrofitWithAuth.create(AbsenceDao::class.java)}
    val advertisingDao: AdvertisingDao by lazy{ retrofitWithAuth.create(AdvertisingDao::class.java)}
    val scheduleDao: ScheduleDao by lazy{ retrofitWithAuth.create(ScheduleDao::class.java)}
    val ratingDao: RatingDao by lazy{ retrofitWithAuth.create(RatingDao::class.java)}
    val studentDao: StudentDao by lazy{ retrofitWithAuth.create(StudentDao::class.java)}
    val accountDao: AccountDao by lazy{ retrofit.create(AccountDao::class.java)}
}