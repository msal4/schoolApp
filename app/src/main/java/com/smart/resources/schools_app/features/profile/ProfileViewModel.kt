package com.smart.resources.schools_app.features.profile

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.myTypes.ConnectionError
import com.smart.resources.schools_app.core.myTypes.ResponseError
import com.smart.resources.schools_app.core.myTypes.Success
import com.smart.resources.schools_app.features.profile.certificate.CertificateModel
import com.smart.resources.schools_app.features.profile.certificate.CertificateRepository
import com.smart.resources.schools_app.features.users.domain.usecase.GetBackendUserIdUseCase
import java.net.HttpURLConnection

enum class CertificateState { UNKNOWN, AVAILABLE, UNAVAILABLE, }

class ProfileViewModel @ViewModelInject constructor(
    application: Application,
    private val getBackendUserIdUseCase: GetBackendUserIdUseCase,
) :
    AndroidViewModel(application) {

    private val c = application.applicationContext

    private val certificateRepo by lazy { CertificateRepository() }
    var certificateModel: CertificateModel? = null
    private val _certificateStatusMsg = MutableLiveData<String>()
    val certificateStatusMsg: LiveData<String> = _certificateStatusMsg

    val certificateState = liveData {
        emit(CertificateState.UNKNOWN)
        val userId = getBackendUserIdUseCase()
//        if (userId == null) {
//            emit(CertificateState.UNAVAILABLE)
//            return@liveData
//        }

        emit(getCertificate(userId))
    }

    private suspend fun getCertificate(studentId: String): CertificateState {
        return when (val res = certificateRepo.getCertificate(studentId)) {
            is Success -> {
                certificateModel = res.data
                _certificateStatusMsg.postValue(c.getString(R.string.available))
                CertificateState.AVAILABLE
            }
            is ResponseError -> {
                val errorMsg = if (res.statusCode != HttpURLConnection.HTTP_NOT_FOUND) c.getString(
                    R.string.error,
                    res.statusCode.toString()
                )
                else c.getString(R.string.unavailable)

                _certificateStatusMsg.postValue(errorMsg)
                CertificateState.UNAVAILABLE
            }
            is ConnectionError -> {
                _certificateStatusMsg.postValue(c.getString(R.string.connection_error))
                CertificateState.UNAVAILABLE
            }
        }
    }


}