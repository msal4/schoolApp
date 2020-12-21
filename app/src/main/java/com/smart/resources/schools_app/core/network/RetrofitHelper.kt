package com.smart.resources.schools_app.core.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hadiyarajesh.flower.calladpater.FlowCallAdapterFactory
import com.smart.resources.schools_app.core.network.callAdapter.MyResultCallAdapter
import com.smart.resources.schools_app.core.typeConverters.retrofit.LocalDateConverter
import com.smart.resources.schools_app.core.typeConverters.retrofit.LocalDateTimeConverter
import com.smart.resources.schools_app.core.typeConverters.retrofit.LocalTimeConverter
import com.smart.resources.schools_app.features.absence.AbsenceClient
import com.smart.resources.schools_app.features.addLecture.AddLectureClient
import com.smart.resources.schools_app.features.advertising.AdvertisingClient
import com.smart.resources.schools_app.features.exam.ExamClient
import com.smart.resources.schools_app.features.fees.FeesClient
import com.smart.resources.schools_app.features.homework.HomeworkClient
import com.smart.resources.schools_app.features.homeworkSolution.data.remoteDataSource.HomeworkSolutionClient
import com.smart.resources.schools_app.features.lecture.LectureClient
import com.smart.resources.schools_app.features.library.LibraryClient
import com.smart.resources.schools_app.features.login.AccountClient
import com.smart.resources.schools_app.features.notification.NotificationsClient
import com.smart.resources.schools_app.features.onlineExam.data.remote.dataSource.AnswersClient
import com.smart.resources.schools_app.features.onlineExam.data.remote.dataSource.OnlineExamsClient
import com.smart.resources.schools_app.features.onlineExam.data.remote.dataSource.QuestionsClient
import com.smart.resources.schools_app.features.profile.certificate.CertificateClient
import com.smart.resources.schools_app.features.rating.RatingClient
import com.smart.resources.schools_app.features.schedule.ScheduleClient
import com.smart.resources.schools_app.features.students.StudentClient
import com.smart.resources.schools_app.features.subject.SubjectClient
import com.snakydesign.watchtower.interceptor.WatchTowerInterceptor
import okhttp3.OkHttpClient
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitHelper {

    private val API_BASE_URL get() = "https://srit-school.com/api/"

    // "http://directorates.srittwo.me/api/"
    val gson: Gson = GsonBuilder()
        .registerTypeAdapter(
            LocalDateTime::class.java,
            LocalDateTimeConverter(),
        ).registerTypeAdapter(
            LocalDate::class.java,
            LocalDateConverter(),
        )
        .registerTypeAdapter(
            LocalTime::class.java,
            LocalTimeConverter(),
        )
        .create()

    private val watchTowerInterceptor by lazy {
        WatchTowerInterceptor()
    }

//    private val loggingInterceptor= HttpLoggingInterceptor().apply {
//        setLevel(HttpLoggingInterceptor.Level.BODY)
//    }

    private val mBuilder
        get() = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(FlowCallAdapterFactory.create())
            .addCallAdapterFactory(MyResultCallAdapter.MyResultCallAdapterFactory())

    private val retrofit: Retrofit
        get() =
            mBuilder
                .client(
                    OkHttpClient
                        .Builder()
                        .addInterceptor(watchTowerInterceptor)
                        .build()
                )
                .build()

    private val retrofitWithAuth: Retrofit
        get() =
            with(OkHttpClient.Builder()) {
                addInterceptor(watchTowerInterceptor)
                addInterceptor(AuthorizationInterceptor.instance)
                mBuilder
                    .client(build())
                    .build()
            }


    val accountClient: AccountClient get() = retrofit.create(AccountClient::class.java)
    val homeworkClient: HomeworkClient get() = retrofitWithAuth.create(HomeworkClient::class.java)
    val homeworkSolutionClient: HomeworkSolutionClient
        get() = retrofitWithAuth.create(
            HomeworkSolutionClient::class.java
        )
    val examClient: ExamClient get() = retrofitWithAuth.create(ExamClient::class.java)
    val onlineExamsClient: OnlineExamsClient get() = retrofitWithAuth.create(OnlineExamsClient::class.java) // TO not break old code
    val questionsClient: QuestionsClient get() = retrofitWithAuth.create(QuestionsClient::class.java) // TO not break old code
    val answersClient: AnswersClient get() = retrofitWithAuth.create(AnswersClient::class.java) // TO not break old code
    val lectureClient: LectureClient get() = retrofitWithAuth.create(LectureClient::class.java)
    val certificateClient: CertificateClient get() = retrofitWithAuth.create(CertificateClient::class.java)
    val libraryClient: LibraryClient get() = retrofitWithAuth.create(LibraryClient::class.java)
    val notificationClient: NotificationsClient get() = retrofitWithAuth.create(NotificationsClient::class.java)
    val absenceClient: AbsenceClient get() = retrofitWithAuth.create(AbsenceClient::class.java)
    val advertisingClient: AdvertisingClient get() = retrofitWithAuth.create(AdvertisingClient::class.java)
    val scheduleClient: ScheduleClient get() = retrofitWithAuth.create(ScheduleClient::class.java)
    val ratingClient: RatingClient get() = retrofitWithAuth.create(RatingClient::class.java)
    val studentClient: StudentClient get() = retrofitWithAuth.create(StudentClient::class.java)
    val subjectClient: SubjectClient get() = retrofitWithAuth.create(SubjectClient::class.java)
    val feesClient: FeesClient get() = retrofitWithAuth.create(FeesClient::class.java)
    val addLectureClient: AddLectureClient get() = retrofitWithAuth.create(AddLectureClient::class.java)
}