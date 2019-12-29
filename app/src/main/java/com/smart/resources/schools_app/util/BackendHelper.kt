package com.smart.resources.schools_app.util

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BackendHelper {
    private const val API_BASE_URL = "http://bushra.srittwo.me/api/"
    private val gson = GsonBuilder() // TODO: set date format
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
                            SharedPrefHelper.getInstance().accessToken ?: return@Interceptor null
                        val newRequest = chain.request().newBuilder()
                            .addHeader("token", token)
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