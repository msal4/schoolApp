package com.smart.resources.schools_app.core.network

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.features.login.LoginActivity
import com.smart.resources.schools_app.features.users.data.UserRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import java.net.HttpURLConnection
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthorizationInterceptor @Inject constructor(@ApplicationContext private val context: Context) :
    Interceptor {
    private suspend fun getToken() =
        UserRepository.instance.getCurrentUserAccount()?.accessToken?.value

    companion object {
        private const val HEADER_AUTHORIZATION = "Authorization"
        private const val TOKEN_VALUE_PREFIX = "Baerer"
        lateinit var instance: AuthorizationInterceptor
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        return runBlocking {
            val newRequest = chain
                .request()
                .newBuilder()
                .addHeader(HEADER_AUTHORIZATION, "$TOKEN_VALUE_PREFIX ${getToken()}")
                .build()

            val res = chain.proceed(newRequest)
            if (res.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
                logout()
            }

            res
        }
    }

    private fun logout() {
        runBlocking {
            UserRepository.instance.deleteCurrentUser()
        }
        Handler(Looper.getMainLooper()).post {
            Toast
                .makeText(
                    context,
                    context.getString(R.string.account_expire),
                    Toast.LENGTH_LONG,
                )
                .show()
            LoginActivity.newInstance(context)
        }
    }
}