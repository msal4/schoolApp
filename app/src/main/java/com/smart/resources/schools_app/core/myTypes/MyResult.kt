package com.smart.resources.schools_app.core.myTypes

import com.orhanobut.logger.Logger
import kotlinx.coroutines.Deferred
import org.json.JSONObject
import retrofit2.Response

sealed class MyResult<out T>
class Success<out T>(val data: T?) : MyResult<T>()
class ResponseError(val statusCode: Int, val errorBody: String, val combinedMsg: String = "status code: $statusCode\nmessage: $errorBody") : MyResult<Nothing>()
class ConnectionError(val exception: Throwable, val message: String = exception.localizedMessage as String) : MyResult<Nothing>()

suspend fun <T> Deferred<Response<T>>.toMyResult(): MyResult<T> =
    try {
        val response = this.await()
        response.toMyResult()
    } catch (e: Exception) {
        Logger.e("exception : ${e.localizedMessage}")
        ConnectionError(e)
    }

fun <T> Response<T>.toMyResult(): MyResult<T> =
    if (this.isSuccessful) {
        Success(this.body())
    } else {
        val errorString= this.errorBody()?.string()
        val errorMsg= extractMessageIfExists(errorString)

        Logger.e("error code: ${this.code()} - msg: $errorMsg")
        ResponseError(
            this.code(),
            errorMsg
        )
    }

private fun extractMessageIfExists(errorString: String?): String {
    return if (errorString.isNullOrBlank()) ""
    else JSONObject(errorString).run {
        try {
            getString("message")
        } catch (e: Exception) {
            errorString.toString()
        }

    }
}