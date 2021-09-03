package kz.yshop.data.remote.Util

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