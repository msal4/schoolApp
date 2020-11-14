package com.smart.resources.schools_app.features.homeworkSolution.domain.viewModel

import android.app.Application
import android.net.Uri
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.myTypes.ConnectionError
import com.smart.resources.schools_app.core.myTypes.Event
import com.smart.resources.schools_app.core.myTypes.ResponseError
import com.smart.resources.schools_app.core.myTypes.Success
import com.smart.resources.schools_app.core.utils.FileUtils
import com.smart.resources.schools_app.features.homeworkSolution.data.model.AddHomeworkSolutionModel
import com.smart.resources.schools_app.features.homeworkSolution.data.model.HomeworkSolutionModel
import com.smart.resources.schools_app.features.homeworkSolution.data.repository.HomeworkSolutionRepository
import com.smart.resources.schools_app.features.homeworkSolution.domain.repository.IHomeworkSolutionRepository
import com.smart.resources.schools_app.features.users.domain.usecase.IGetCurrentLocalUserIdUseCase
import id.zelory.compressor.Compressor
import kotlinx.coroutines.launch
import java.io.File


class AddHomeworkSolutionViewModel @ViewModelInject constructor(
    application: Application,
    private val getCurrentUserIdUseCase: IGetCurrentLocalUserIdUseCase
)
    : AndroidViewModel(application){
    private val c = application.applicationContext
    private val solutionRepository: IHomeworkSolutionRepository = HomeworkSolutionRepository()

    private val _error = MutableLiveData<Event<String>>()
    val error: LiveData<Event<String>> = _error
    private val _solutionSent = MutableLiveData<Event<HomeworkSolutionModel>>()
    val solutionSent: LiveData<Event<HomeworkSolutionModel>> = _solutionSent
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    lateinit var homeworkId: String
    var description: String = ""
    private var attachmentImage: File? = null

    private val studentId = liveData {
        emit(getCurrentUserIdUseCase())
    }


    fun saveUri(uri: Uri) {
        val originalFile: File = FileUtils.from(c, uri)
        attachmentImage = Compressor(c).compressToFile(originalFile)
    }

    fun sendSolution() {
        if (!isDataValid()) return
        val model = AddHomeworkSolutionModel(
            solution = description,
            attachmentImage = attachmentImage
        )

        viewModelScope.launch {
            _isLoading.postValue(true)
            val res = solutionRepository.addSolution(
                studentId = studentId.value?:"",
                homeworkId = homeworkId,
                addHomeworkSolutionModel = model
            )
            when (res) {
                is Success -> {
                    if (res.data != null) {
                        _solutionSent.postValue(Event(res.data))
                    } else {
                        _error.postValue(Event(c.getString(R.string.unexpected_error)))
                    }
                }
                is ResponseError -> {
                    _error.postValue(Event(c.getString(R.string.error, res.statusCode.toString())))
                }
                is ConnectionError -> {
                    _error.postValue(Event(c.getString(R.string.connection_error)))
                }
            }
            _isLoading.postValue(false)
        }
    }

    private fun isDataValid(): Boolean {
        if (description.isBlank() && attachmentImage == null) {
            _error.value = Event(c.getString(R.string.empty_answer_msg))
            return false
        }
        if (!::homeworkId.isInitialized || homeworkId.isBlank() || studentId.value.isNullOrBlank()) {
            _error.value = Event(c.getString(R.string.unexpected_error))
            return false
        }
        return true
    }
}

