package com.smart.resources.schools_app.util

import com.orhanobut.logger.Logger
import kotlinx.coroutines.Deferred
import retrofit2.Response

sealed class MyResult<out T>{
    companion object{
        suspend fun <T>fromResponse(deferred: Deferred<Response<T>>):MyResult<T>  =
            try{
                val response=  deferred.await()
            if (response.isSuccessful) {
                Success(response.body())
            } else {
                Logger.e("error code: ${response.code()} - msg: ${response.errorBody()}")
                ResponseError(response.code(), response.errorBody()?.toString().orEmpty())
            }
        }catch (e: Exception){
            Logger.e("exception : ${e.localizedMessage}")
            ConnectionError(e)
        }
    }
}
class Success<out T>(val data: T?) : MyResult<T>()
class ResponseError(val statusCode: Int, val errorBody: String, val combinedMsg: String = "status code: $statusCode, error body: $errorBody") : MyResult<Nothing>()
class ConnectionError(val exception: Throwable, val message: String = exception.localizedMessage as String) : MyResult<Nothing>()