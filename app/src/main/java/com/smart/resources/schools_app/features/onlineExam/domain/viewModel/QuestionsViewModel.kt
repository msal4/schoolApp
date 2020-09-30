package com.smart.resources.schools_app.features.onlineExam.domain.viewModel

import androidx.lifecycle.*
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.features.login.CanLogout
import com.smart.resources.schools_app.features.onlineExam.domain.model.*

typealias ListOfAnswerableQuestions = List<BaseAnswerableQuestion<out Any>>

class QuestionsViewModel(val onlineExam: OnlineExam, val readOnly: Boolean) : ViewModel(), CanLogout {

    private val _questions:MutableLiveData<ListOfAnswerableQuestions> = MutableLiveData(dummyAnswerableQuestions)
    val questions: LiveData<ListOfAnswerableQuestions> = _questions
    val solvedQuestions = questions.switchMap {
        questions.map { it.map { q -> q.answer!=null } }
    }
    val remainingQuestionsText = solvedQuestions.map {
        "${it.filter { solved -> !solved }.size}/${it.size}"
    }

    fun onTimerFinished() {
        Logger.wtf("timer finished")
    }

    fun updateAnswer(answer: BaseAnswer<out Any>, position: Int) {

    }
}


class QuestionsViewModelFactory(
    private val onlineExam: OnlineExam,
    private val readOnly:Boolean
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return QuestionsViewModel(onlineExam, readOnly) as T
    }
}


private val dummyAnswerableQuestions:MutableList<BaseAnswerableQuestion<out Any>> = mutableListOf(
    AnswerableQuestion(
        questionId = "0",
        question = " ما هو الحيوان الذي إن تم قطع رجل من أرجله تنمو مجدداً؟",
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