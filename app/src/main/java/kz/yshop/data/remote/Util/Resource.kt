package kz.yshop.data.remote.Util

import android.util.Log
import retrofit2.Response

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
    val errorData: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class NoContent<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(data: T? = null,message: String,errorData: String? = null) : Resource<T>(data, message, errorData)
    class Unauthorized<T>(data: T? = null) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class NoInternet<T>(message: String? = null) : Resource<T>(null, message)
}

fun <T : Any> checkResponse(data: Response<T>): Resource<T> {
    if (data.isSuccessful) {
        return when (data.code()) {
            in 200..202 -> Resource.Success(data.body()!!)
            204 -> {
                Log.wtf("noContent", data.toString())
                Resource.NoContent()
            }
            else -> Resource.Error(data.body(), data.message(), data.errorBody()!!.string())
        }
    } else {
        if (data.code() in 400..499) {
            return Resource.Unauthorized()
        }
        return Resource.Error(data.body(), data.message(), data.errorBody()!!.string())
    }
}