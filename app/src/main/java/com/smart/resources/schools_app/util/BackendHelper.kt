package com.smart.resources.schools_app.util

import com.google.gson.GsonBuilder
import com.smart.resources.schools_app.adapter.LocalDateTimeConverter
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.threeten.bp.LocalDateTime
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BackendHelper {
    private const val API_BASE_URL = "http://kother.srittwo.me/api/" // TODO: change1
    val gson = GsonBuilder()
        .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeConverter())
        // TODO: set date format
        .create()


    private val mBuilder = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .addConverterFactory(
            GsonConverterFactory.create(gson)
        )

    // TODO: deal with timeout
    private val mHttpClient = OkHttpClient.Builder()
    private val mHttpClientWithInterceptor = OkHttpClient.Builder()


    val retrofit: Retrofit
        get() = mBuilder
            .client(mHttpClient.build())
            .build()

    val retrofitWithAuth: Retrofit
        get() {
            if (mHttpClientWithInterceptor.interceptors().size == 0) {
                mHttpClientWithInterceptor.addInterceptor(
                    Interceptor { chain ->
                        val token =
                            SharedPrefHelper.instance?.accessToken ?: return@Interceptor null
                        val newRequest = chain.request().newBuilder()
                            .addHeader("Authorization", "Baerer $token")
                            .build()
                        chain.proceed(newRequest)
                    }
                )
            }
            return mBuilder
                .client(mHttpClientWithInterceptor.build())
                .build()
        }
}