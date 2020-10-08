package com.smart.resources.schools_app.features.onlineExam.domain.viewModel

import android.app.Application
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.hadiyarajesh.flower.Resource
import com.haytham.coder.extensions.toString
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.myTypes.ListState
import com.smart.resources.schools_app.core.myTypes.UserType
import com.smart.resources.schools_app.core.typeConverters.room.OnlineExamStatus
import com.smart.resources.schools_app.core.typeConverters.room.QuestionType
import com.smart.resources.schools_app.features.onlineExam.domain.model.*
import com.smart.resources.schools_app.features.onlineExam.domain.model.onlineExam.OnlineExam
import com.smart.resources.schools_app.features.onlineExam.domain.usecase.IGetExamQuestionsUseCase
import com.smart.resources.schools_app.features.onlineExam.domain.usecase.IGetOnlineExamUseCase
import com.smart.resources.schools_app.features.onlineExam.presentation.fragments.ExamPaperFragment
import com.smart.resources.schools_app.features.users.domain.usecase.IGetCurrentUserTypeUseCase
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

typealias ListOfAnswerableQuestions = List<BaseAnswerableQuestion<out Any>>

class ExamPaperViewModel @ViewModelInject constructor(
    private val getExamQuestionUseCase: IGetExamQuestionsUseCase,
    private val getCurrentUserTypeUseCase: IGetCurrentUserTypeUseCase,
    private val getOnlineExamUseCase: IGetOnlineExamUseCase,
    @Assisted private val savedStateHandle: SavedStateHandle,
    application: Application
) : AndroidViewModel(application) {

    private val c = application.applicationContext
    val listState = ListState()
    //        MutableLiveData(dummyAnswerableQuestions)
    //    private val _questions: MutableLiveData<ListOfAnswerableQuestions> =

    private val initialOnlineExam: OnlineExam get() = savedStateHandle.get(ExamPaperFragment.EXTRA_EXAM_DETAILS)!!
    private val userType: UserType = getCurrentUserTypeUseCase()
    val onlineExam = liveData {
        emit(initialOnlineExam)

        val examLiveData= getOnlineExamUseCase(initialOnlineExam.id)
            .map { it.data }
            .filterNotNull()
            .asLiveData(viewModelScope.coroutineContext)
        emitSource(examLiveData)
    }

    val readOnly = onlineExam.map {
        !(it.examStatus == OnlineExamStatus.ACTIVE
                && userType == UserType.STUDENT)
    }


    val questions: LiveData<ListOfAnswerableQuestions> =
        getExamQuestionUseCase(initialOnlineExam.id).map {
            // if data is empty show message, or loading indicator
            if (it.data.isNullOrEmpty()) {
                updateListState(it)
            }

            // map questions any way
            mapQuestionToAnswerableQuestions(it).apply {
                listState.setLoading(false)
            }
        }.asLiveData(viewModelScope.coroutineContext)


    val solvedQuestions = questions.switchMap {
        questions.map { it.map { q -> q.answer != null } }
    }
    val remainingQuestionsText = solvedQuestions.map {
        "${it.filter { solved -> !solved }.size}/${it.size}"
    }

    private fun updateListState(it: Resource<List<Question>>) {
        when (it.status) {
            Resource.Status.SUCCESS -> {
                listState.setBodyError(R.string.no_questions.toString(c))
            }
            Resource.Status.ERROR -> {
                Logger.e(it.message.toString())
                listState.setBodyError(R.string.connection_error.toString(c))
            }
            Resource.Status.LOADING -> {
                listState.setLoading(true)
            }
        }
    }

    private fun mapQuestionToAnswerableQuestions(it: Resource<List<Question>>): ListOfAnswerableQuestions {
        return it.data.orEmpty().map { question ->
            when (question.questionType) {
                QuestionType.MULTI_CHOICE -> {
                    MultiChoiceAnswerableQuestion(
                        question = question,
                        answer = null,
                    )
                }
                QuestionType.CORRECTNESS -> {
                    CorrectnessAnswerableQuestion(
                        question = question,
                        answer = null,
                    )
                }
                QuestionType.NORMAL -> {
                    AnswerableQuestion(
                        question = question,
                        answer = null,
                    )
                }
            }
        }
    }

    fun onTimerFinished() {
        Logger.wtf("timer finished")
    }

    fun updateAnswer(answer: BaseAnswer<out Any>, position: Int) {

    }
}


//class QuestionsViewModelFactory(
//    private val onlineExam: OnlineExam,
//    private val readOnly: Boolean,
//    private val getOnlineExamQuestionUseCase: IGetExamQuestionsUseCase,
//) :
//    ViewModelProvider.Factory {
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        return ExamPaperViewModel(onlineExam, readOnly, getOnlineExamQuestionUseCase) as T
//    }
//}


private val dummyAnswerableQuestions: MutableList<BaseAnswerableQuestion<out Any>> = mutableListOf(
    AnswerableQuestion(
        questionId = "0",
        question = "ما هو الحيوان الذي إن تم قطع رجل من أرجله تنمو مجدداً؟",
        answer = "hello Sir",
    ),
    MultiChoiceAnswerableQuestion(
        questionId = "1",
        question = "النحلة ترفرف بجناحيها في الثانية الواحدة بمعدل….؟",
        options = listOf(
            "150 مرة",
            "250 مرة",
            "350 مرة"
        ),
        answer = 1,
    ),
    CorrectnessAnswerableQuestion(
        questionId = "2",
        question = "ان الشعور بالاكتئاب وتقلب المزاج مرتبط بنقص معدن المغنيسوم",
        answer = true
    ),

    AnswerableQuestion(
        questionId = "3",
        question = "هل الأطفال أقل تعرضاً لمخاطر الإصابة بـكوفيد-19 مقارنة بالبالغين؟",
    ),
    MultiChoiceAnswerableQuestion(
        questionId = "4",
        question = "العالم الذي أخترع التلفون هو…؟",
        options = listOf(
            "الكسندر غراهام بيل",
            "جوزف برستلي",
            "بجوار سيكو رسكي"
        ),
    ),
    CorrectnessAnswerableQuestion(
        questionId = "5",
        question = "التوتر قد يصيب بشرتك بحالات من الطفح الجلدي الحاد؟",
    ),
)