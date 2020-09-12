package com.smart.resources.schools_app.core.helpers

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.smart.resources.schools_app.core.adapters.LocalDateTimeConverter
import com.smart.resources.schools_app.features.absence.AbsenceClient
import com.smart.resources.schools_app.features.advertising.AdvertisingClient
import com.smart.resources.schools_app.features.exam.ExamClient
import com.smart.resources.schools_app.features.homework.HomeworkClient
import com.smart.resources.schools_app.features.homeworkSolution.data.remoteDataSource.HomeworkSolutionClient
import com.smart.resources.schools_app.features.lecture.LectureClient
import com.smart.resources.schools_app.features.library.LibraryClient
import com.smart.resources.schools_app.features.login.AccountClient
import com.smart.resources.schools_app.features.notification.NotificationsClient
import com.smart.resources.schools_app.features.profile.certificate.CertificateClient
import com.smart.resources.schools_app.features.rating.RatingClient
import com.smart.resources.schools_app.features.schedule.ScheduleClient
import com.smart.resources.schools_app.features.students.StudentClient
import com.smart.resources.schools_app.features.users.UsersRepository
import com.snakydesign.watchtower.WatchTower
import com.snakydesign.watchtower.interceptor.WatchTowerInterceptor
import com.snakydesign.watchtower.interceptor.WebWatchTowerObserver
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.threeten.bp.LocalDateTime
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitHelper {

  private  val API_BASE_URL get() =  "https://educatoin.app/api/"
    // "http://directorates.srittwo.me/api/"
    val gson: Gson = GsonBuilder()
        .registerTypeAdapter(
            LocalDateTime::class.java,
            LocalDateTimeConverter()
        )
        .create()


    private val loggingInterceptor by lazy {
        WatchTower.start(WebWatchTowerObserver(port = 8085))// TODO: move to app class
        WatchTowerInterceptor()
        // HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) }
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
                    UsersRepository.instance.getCurrentUserAccount()?.accessToken
                val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Baerer $token")
                    .build()
                chain.proceed(newRequest)
            }
        )
    }

    val homeworkClient: HomeworkClient get() =  retrofitWithAuth.create(HomeworkClient::class.java)
    val homeworkSolutionClient: HomeworkSolutionClient
        get() =  retrofitWithAuth.create(
            HomeworkSolutionClient::class.java)
    val examClient: ExamClient get() = retrofitWithAuth.create(ExamClient::class.java)
    val lectureClient: LectureClient get() = retrofitWithAuth.create(LectureClient::class.java)
    val certificateClient: CertificateClient get() = retrofitWithAuth.create(CertificateClient::class.java)
    val libraryClient: LibraryClient get() =  retrofitWithAuth.create(LibraryClient::class.java)
    val notificationClient: NotificationsClient get() =  retrofitWithAuth.create(NotificationsClient::class.java)
    val absenceClient: AbsenceClient get() = retrofitWithAuth.create(AbsenceClient::class.java)
    val advertisingClient: AdvertisingClient get() =  retrofitWithAuth.create(AdvertisingClient::class.java)
    val scheduleClient: ScheduleClient get() =  retrofitWithAuth.create(ScheduleClient::class.java)
    val ratingClient: RatingClient get() =  retrofitWithAuth.create(RatingClient::class.java)
    val studentClient: StudentClient get() =  retrofitWithAuth.create(StudentClient::class.java)
    val accountClient: AccountClient get() =  retrofit.create(AccountClient::class.java)
}