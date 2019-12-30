package com.smart.resources.schools_app.util

import com.orhanobut.logger.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.*

abstract class BackendCallBack<T> : Callback<T> {
    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful) {
            onSuccess(response.body())
            onFinish()
        } else {
            try {
                val errorBody = response.errorBody()?.string()
                Logger.e("<<<onResponse error>>>\nCode: %s\nMessage: %s\nBody: %s",
                        response.code(), response.message(), errorBody)
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                onError(response.code(), Objects.requireNonNull(response.errorBody()).toString())
                onFinish()
            }
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        Logger.e("<<<onFailure error>>>\nMessage: %s", t.message)
        onFinish()
    }

    abstract fun onSuccess(result: T?)
    abstract fun onError(code: Int, msg: String?)
    abstract fun onFinish()
}