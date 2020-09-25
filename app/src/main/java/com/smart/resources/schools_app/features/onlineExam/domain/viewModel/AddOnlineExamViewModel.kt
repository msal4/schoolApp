package com.smart.resources.schools_app.features.onlineExam.domain.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.smart.resources.schools_app.features.login.CanLogout
import com.smart.resources.schools_app.features.onlineExam.domain.model.Question
import com.smart.resources.schools_app.features.onlineExam.domain.model.QuestionType
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime


class AddOnlineExamViewModel(application: Application) : AndroidViewModel(application),
    CanLogout {
    private val c = application.applicationContext

    var examDate: LocalDate? = null
    var examTime: LocalTime? = null

    private val _questions = MutableLiveData(
        listOf(
            Question(
                id = "0",
                question = "ما هو الحيوان الذي إن تم قطع رجل من أرجله تنمو مجدداً؟",
                questionType = QuestionType.NORMAL,
            ),
            Question(
                id = "1",
                question = "النحلة ترفرف بجناحيها في الثانية الواحدة بمعدل….؟",
                options = listOf(
                     "150 مرة",
                     "250 مرة",
                     "350 مرة",
                ),
                questionType = QuestionType.MULTI_CHOICE,
            ),
            Question(
                id = "2",
                question = "ان الشعور بالاكتئاب وتقلب المزاج مرتبط بنقص معدن المغنيسوم",
                questionType = QuestionType.CORRECTNESS,
            ),
        ),
    )
    val questions: LiveData<List<Question>> = _questions
}