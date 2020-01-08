package com.smart.resources.schools_app.features.section
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.features.rating.RatingAdapter
import com.smart.resources.schools_app.features.exam.ExamDao
import com.smart.resources.schools_app.features.exam.ExamModel
import com.smart.resources.schools_app.features.exam.ExamRecyclerAdapter
import com.smart.resources.schools_app.features.homework.HomeworkDao
import com.smart.resources.schools_app.features.homework.HomeworkModel
import com.smart.resources.schools_app.features.homework.HomeworkRecyclerAdapter
import com.smart.resources.schools_app.features.library.LibraryDao
import com.smart.resources.schools_app.features.library.LibraryModel
import com.smart.resources.schools_app.features.library.LibraryRecyclerAdapter
import com.smart.resources.schools_app.features.schedule.ScheduleDao
import com.smart.resources.schools_app.features.schedule.ScheduleDayModel
import com.smart.resources.schools_app.features.schedule.WeekRecyclerAdapter
import com.smart.resources.schools_app.features.studentAbsence.AbsenceRecyclerAdapter
import com.smart.resources.schools_app.features.studentAbsence.StudentAbsenceDao
import com.smart.resources.schools_app.features.studentAbsence.StudentAbsenceModel
import com.smart.resources.schools_app.core.util.*
import com.smart.resources.schools_app.core.util.SectionViewModelListener
import com.smart.resources.schools_app.features.advertising.AdvertisingDao
import com.smart.resources.schools_app.features.advertising.AdvertisingModel
import com.smart.resources.schools_app.features.advertising.AdvertisingRecyclerAdapter
import com.smart.resources.schools_app.features.rating.RatingModel
import com.smart.resources.schools_app.features.notification.NotificationsDao
import com.smart.resources.schools_app.features.rating.RatingDao
import kotlinx.coroutines.*


class SectionViewModel(private val section:Section,
                       private val viewModelListener: SectionViewModelListener,
                       application: Application)
    : AndroidViewModel(application) {
    private val context= application.applicationContext


    fun downloadData() =
        viewModelScope.launch{
            val result = sendRequest()

            withContext(Dispatchers.Main){
                var errorMsg= ""
                when(result){
                    is Success -> {
                        if(result.data.isNullOrEmpty()) errorMsg= getEmptyListMsg()
                        else viewModelListener.onDataDownloaded(getAdapter(result))
                    }
                    is ResponseError -> errorMsg= result.combinedMsg
                    is ConnectionError -> errorMsg=  context.getString(R.string.connection_error)
                }

                viewModelListener.onError(errorMsg)
            }
        }

    private fun getEmptyListMsg(): String {
        val stringId= when (section) {
            Section.HOMEWORK -> R.string.no_homework
            Section.LIBRARY ->  R.string.no_library
            Section.SCHEDULE ->  R.string.no_schedule
            Section.ABSENCE ->  R.string.no_student_absence
            Section.RATE ->  R.string.rate
            Section.ADVERTISING ->  R.string.advertising
            else -> 0
        }

        return context.getString(stringId)
    }

    private fun getAdapter(result: Success<List<Any>>) = when (section) {
        Section.SCHEDULE -> setupWeekRecycler(result)
        Section.ABSENCE -> AbsenceRecyclerAdapter(
            result.data as List<StudentAbsenceModel>
        )
        Section.RATE -> RatingAdapter(
            result.data as List<RatingModel>
        )
        Section.ADVERTISING ->  AdvertisingRecyclerAdapter(result.data as List<AdvertisingModel>)
        else -> throw Exception()
    }

    private fun setupWeekRecycler(result: Success<List<Any>>) =
        WeekRecyclerAdapter(
            mapWeekRecyclerData(result)
        )
            .apply {
                onClick = {
                    viewModelListener.onScheduleItemClicked(it)
                }
            }

    private fun mapWeekRecyclerData(result: Success<List<Any>>) =
            (result.data as List<List<String?>>)
                .mapIndexed { index, list ->
                    ScheduleDayModel(
                        WeekDays.getDayName(index),
                        list
                    )
                }
                .filter {!it.dayList.isNullOrEmpty()}

    private suspend fun sendRequest() = when (section) {
             Section.SCHEDULE -> {
                 BackendHelper.retrofitWithAuth.create(ScheduleDao::class.java)
                     .run{GlobalScope.async {fetchSchedule()}.toMyResult()}
            }
            Section.ABSENCE -> {
                BackendHelper.retrofitWithAuth.create(StudentAbsenceDao::class.java)
                    .run{ GlobalScope.async {fetchStudentAbsence()}.toMyResult()}
            }

            Section.RATE -> {
            BackendHelper.retrofitWithAuth.create(RatingDao::class.java)
                .run{ GlobalScope.async {fetchRating()}.toMyResult()}
            }

            Section.ADVERTISING -> {
            BackendHelper.retrofitWithAuth.create(AdvertisingDao::class.java)
                .run{ GlobalScope.async {fetchAdvertisements()}.toMyResult()}
             }
        else ->  throw Exception()
    }
}

class SectionViewModelFactory(
    private val mApplication: Application,
    private val section: Section,
    private val listener: SectionViewModelListener
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SectionViewModel(
            section,
            listener,
            mApplication
        ) as T
    }
}