package com.smart.resources.schools_app.features.onlineExam.domain.viewModel

import androidx.lifecycle.*
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.core.Event
import com.smart.resources.schools_app.features.login.CanLogout
import com.smart.resources.schools_app.features.onlineExam.domain.model.OnlineExamDetails
import com.smart.resources.schools_app.features.onlineExam.domain.model.questions.BaseQuestion
import com.smart.resources.schools_app.features.onlineExam.domain.model.questions.CorrectnessQuestion
import com.smart.resources.schools_app.features.onlineExam.domain.model.questions.MultiChoiceQuestion
import com.smart.resources.schools_app.features.onlineExam.domain.model.questions.Question

typealias ListOfBaseQuestions= List<BaseQuestion<out Any?>>

class QuestionsViewModel(val onlineExamDetails: OnlineExamDetails)
    : ViewModel(), CanLogout {

    private val _questions = MutableLiveData(dummyQuestions)
    val questions: LiveData<ListOfBaseQuestions> = _questions

    val answersStateUpdatedEvent = MutableLiveData(Event(true))

    val solvedQuestions =  answersStateUpdatedEvent.switchMap{
        questions.map { it.map { q -> q.isAnswered } }
    }
    val remainingQuestionsText= solvedQuestions.map {
        "${it.filter { solved -> !solved }.size}/${it.size}"
    }

    fun onTimerFinished() {

        Logger.wtf("timer finished")
    }

}


class QuestionsViewModelFactory(
    private val onlineExamDetails: OnlineExamDetails
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return QuestionsViewModel(onlineExamDetails) as T
    }
}


private val dummyQuestions = listOf(
    Question(
        id = "0",
        text = " ما هو الحيوان الذي إن تم قطع رجل من أرجله تنمو مجدداً؟",
        answer = "hello Sir"
    ),
    MultiChoiceQuestion(
        id = "1", text = "النحلة ترفرف بجناحيها في الثانية الواحدة بمعدل….؟", choices = listOf(
            "150 مرة",
            "250 مرة",
            "350 مرة"
        )
    ),
    CorrectnessQuestion(
        id = "2",
        text = "ان الشعور بالاكتئاب وتقلب المزاج مرتبط بنقص معدن المغنيسوم",
        answer = true
    ),
    Question(id = "3", text = "هل الأطفال أقل تعرضاً لمخاطر الإصابة بـكوفيد-19 مقارنة بالبالغين؟"),
    MultiChoiceQuestion(
        id = "4", text = "العالم الذي أخترع التلفون هو…؟", choices = listOf(
            "الكسندر غراهام بيل",
            "جوزف برستلي",
            "بجوار سيكو رسكي"
        )
    ),
    CorrectnessQuestion(id = "5", text = "التوتر قد يصيب بشرتك بحالات من الطفح الجلدي الحاد؟"),
)