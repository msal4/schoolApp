package com.smart.resources.schools_app.features.onlineExam.domain.viewModel

import android.app.Application
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.hadiyarajesh.flower.Resource
import com.haytham.coder.extensions.isNotNullOrEmpty
import com.haytham.coder.extensions.toString
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.extentions.TAG
import com.smart.resources.schools_app.core.myTypes.ListState
import com.smart.resources.schools_app.core.myTypes.UserType
import com.smart.resources.schools_app.core.typeConverters.room.OnlineExamStatus
import com.smart.resources.schools_app.core.typeConverters.room.QuestionType
import com.smart.resources.schools_app.features.onlineExam.domain.model.*
import com.smart.resources.schools_app.features.onlineExam.domain.model.onlineExam.OnlineExam
import com.smart.resources.schools_app.features.onlineExam.domain.usecase.*
import com.smart.resources.schools_app.features.onlineExam.domain.usecase.questions.GetExamQuestionsWithAnswersUseCase
import com.smart.resources.schools_app.features.onlineExam.domain.usecase.questions.SyncOnlineExamUseCase
import com.smart.resources.schools_app.features.onlineExam.presentation.fragments.ExamPaperFragment
import com.smart.resources.schools_app.features.users.domain.usecase.IGetCurrentUserTypeUseCase
import com.smart.resources.schools_app.features.users.domain.usecase.IGetUserIdUseCase
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

typealias ListOfAnswerableQuestions = List<BaseAnswerableQuestion<Any>>

class ExamPaperViewModel @ViewModelInject constructor(
    private val getCurrentUserTypeUseCase: IGetCurrentUserTypeUseCase,
    private val getOnlineExamUseCase: IGetOnlineExamUseCase,
    private val syncOnlineExamUseCase: ISyncOnlineExamUseCase,
    private val saveAnswerLocallyUseCase: ISaveAnswerLocallyUseCase,
    private val sendAnswersUseCase: ISendAnswersUseCase,
    private val getUserIdUseCase: IGetUserIdUseCase,
    private val getExamQuestionsWithAnswersUseCase: IGetExamQuestionsWithAnswersUseCase,
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


    private val userId:LiveData<String> = liveData { emit(getUserIdUseCase()) }
    val questions: LiveData<ListOfAnswerableQuestions> = userId.switchMap { userId ->
        getExamQuestionsWithAnswersUseCase(initialOnlineExam.id, userId).map {
            updateListState(it)
            it.data.orEmpty()
        }.asLiveData(viewModelScope.coroutineContext)
    }

    val solvedQuestions = questions.switchMap {
        questions.map { it.map { q -> q.answer != null } }
    }
    val remainingQuestionsText = solvedQuestions.map {
        "${it.filter { solved -> !solved }.size}/${it.size}"
    }

    private fun updateListState(it: Resource<List<Any>>) {
        if(it.data.isNotNullOrEmpty()) listState.setLoading(false)
        else when (it.status) {
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
        Logger.d("$TAG: $it")
    }

//    private fun mapQuestionToAnswerableQuestions(it: Resource<List<Question>>): ListOfAnswerableQuestions {
//        return it.data.orEmpty().map { question ->
//            when (question.questionType) {
//                QuestionType.MULTI_CHOICE -> {
//                    MultiChoiceAnswerableQuestion(
//                        question = question,
//                        answer = null,
//                    )
//                }
//                QuestionType.CORRECTNESS -> {
//                    CorrectnessAnswerableQuestion(
//                        question = question,
//                        answer = null,
//                    )
//                }
//                QuestionType.DEFINE -> {
//                    AnswerableQuestion(
//                        question = question,
//                        answer = null,
//                    )
//                }
//            }
//        }
//    }

    fun onTimerFinished() {
    }

    fun checkExamStatus(){
        onlineExam.value?.id?.let {
            viewModelScope.launch {
                syncOnlineExamUseCase(it)
            }
        }
    }

    fun updateAnswer(answer: BaseAnswer<Any>, position: Int) {
        viewModelScope.launch {
            questions.value?.getOrNull(position)?.id?.let { saveAnswerLocallyUseCase(answer, it) }
        }
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


private val dummyAnswerableQuestions: MutableList<BaseAnswerableQuestion<Any>> = mutableListOf(
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