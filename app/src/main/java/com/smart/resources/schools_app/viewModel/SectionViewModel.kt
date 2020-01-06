package com.smart.resources.schools_app.viewModel
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.adapter.sections.*
import com.smart.resources.schools_app.database.dao.*
import com.smart.resources.schools_app.database.model.*
import com.smart.resources.schools_app.util.*
import kotlinx.coroutines.*


class SectionViewModel(private val section:Section,
                       private val viewModelListener: SectionViewModelListener,
                       application: Application)
    : AndroidViewModel(application) {
    private val context= application.applicationContext


    fun downloadData() =
        CoroutineScope(Dispatchers.IO).launch{
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
            Section.EXAM ->  R.string.no_exams
            Section.LIBRARY ->  R.string.no_library
            Section.SCHEDULE ->  R.string.no_schedule
            Section.ABSENCE ->  R.string.no_student_absence
        }

        return context.getString(stringId)
    }

    private fun getAdapter(result: Success<List<Any>>) = when (section) {
        Section.HOMEWORK -> HomeworkRecyclerAdapter(result.data as List<HomeworkModel>)
        Section.EXAM -> ExamRecyclerAdapter(result.data as List<ExamModel>)
        Section.LIBRARY -> LibraryRecyclerAdapter(result.data as List<LibraryModel>)
        Section.SCHEDULE -> setupWeekRecycler(result)
        Section.ABSENCE -> AbsenceRecyclerAdapter(result.data as List<StudentAbsenceModel>)
    }

    private fun setupWeekRecycler(result: Success<List<Any>>) =
        WeekRecyclerAdapter(mapWeekRecyclerData(result))
            .apply {
                onClick = {
                    viewModelListener.onScheduleItemClicked(it)
                }
            }

    private fun mapWeekRecyclerData(result: Success<List<Any>>) =
            (result.data as List<List<String?>>)
                .mapIndexed { index, list ->  ScheduleDayModel( WeekDays.getDayName(index), list) }
                .filter {!it.dayList.isNullOrEmpty()}

    private suspend fun sendRequest() = when (section) {
            Section.HOMEWORK -> {
                BackendHelper.retrofitWithAuth.create(HomeworkDao::class.java)
                    .run{ MyResult.fromResponse(GlobalScope.async {fetchHomework()})}
            }
            Section.EXAM -> {
                BackendHelper.retrofitWithAuth.create(ExamDao::class.java)
                    .run{ MyResult.fromResponse(GlobalScope.async {fetchExam()})}
            }

            Section.LIBRARY ->{
                 BackendHelper.retrofitWithAuth.create(LibraryDao::class.java)
                     .run{ MyResult.fromResponse(GlobalScope.async {fetchLib()})}
            }

             Section.SCHEDULE -> {
                 BackendHelper.retrofitWithAuth.create(ScheduleDao::class.java)
                     .run{ MyResult.fromResponse(GlobalScope.async {fetchSchedule()})}
            }
            Section.ABSENCE -> {
                BackendHelper.retrofitWithAuth.create(StudentAbsenceDao::class.java)
                    .run{ MyResult.fromResponse(GlobalScope.async {fetchStudentAbsence()})}
            }
        }
}

class SectionViewModelFactory(
    private val mApplication: Application,
    private val section: Section,
    private val listener:SectionViewModelListener) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SectionViewModel(section, listener, mApplication) as T
    }
}