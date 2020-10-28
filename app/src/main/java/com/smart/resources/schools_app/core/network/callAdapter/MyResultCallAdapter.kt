package com.smart.resources.schools_app.core.network.callAdapter

import com.smart.resources.schools_app.core.myTypes.MyResult
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class MyResultCallAdapter(
    private val type: Type
): CallAdapter<Type, Call<MyResult<Type>>> {
    override fun responseType() = type
    override fun adapt(call: Call<Type>): Call<MyResult<Type>> = ResultCall(call)

    class MyResultCallAdapterFactory : CallAdapter.Factory() {
        override fun get(
            returnType: Type,
            annotations: Array<Annotation>,
            retrofit: Retrofit
        ) = when (getRawType(returnType)) {
            Call::class.java -> {
                val callType = getParameterUpperBound(0, returnType as ParameterizedType)
                when (getRawType(callType)) {
                    MyResult::class.java -> {
                        val resultType = getParameterUpperBound(0, callType as ParameterizedType)
                        MyResultCallAdapter(resultType)
                    }
                    else -> null
                }
            }
            else -> null
        }
    }
}

