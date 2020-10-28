package com.smart.resources.schools_app.core.network.callAdapter

import com.smart.resources.schools_app.core.myTypes.ConnectionError
import com.smart.resources.schools_app.core.myTypes.MyResult
import com.smart.resources.schools_app.core.myTypes.toMyResult
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultCall<T>(proxy: Call<T>) : CallDelegate<T, MyResult<T>>(proxy) {
    override fun enqueueImpl(callback: Callback<MyResult<T>>) = proxy.enqueue(object:
        Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            val result= response.toMyResult()

            callback.onResponse(this@ResultCall, Response.success(result))
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            val result = ConnectionError(t)

            callback.onResponse(this@ResultCall, Response.success(result))
        }
    })

    override fun cloneImpl() = ResultCall(proxy.clone())
    override fun timeout(): Timeout = Timeout.NONE
}