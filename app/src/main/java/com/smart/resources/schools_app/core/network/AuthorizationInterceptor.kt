package com.smart.resources.schools_app.core.network

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.smart.resources.schools_app.features.login.CanLogout
import com.smart.resources.schools_app.features.users.data.UserRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import java.net.HttpURLConnection
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthorizationInterceptor @Inject constructor(@ApplicationContext private val context: Context) :
    Interceptor, CanLogout {
    private val token:String? get() = UserRepository.instance.getCurrentUserAccount()?.accessToken?.value

    companion object {
        private const val HEADER_AUTHORIZATION = "Authorization"
        private const val TOKEN_VALUE_PREFIX = "Baerer"
        lateinit var instance: AuthorizationInterceptor
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain
            .request()
            .newBuilder()
            .addHeader(HEADER_AUTHORIZATION, "$TOKEN_VALUE_PREFIX $token")
            .build()

        val res = chain.proceed(newRequest)
        if (res.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
            logout()
        }

        return res
    }

    private fun logout() {
        Handler(Looper.getMainLooper()).post {
            expireLogout(context)
        }
    }
}