package kz.yshop.data.remote.interceptors


import kz.yshop.util.Constants.userMainHash
import okhttp3.Interceptor
import okhttp3.Response


class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val newRequest = original.newBuilder()
            .header("Authorization", "Bearer $userMainHash")
            .header("platform", "android")
            .build()
        //Log.wtf("haash", language + " " + userMainHash)
        return chain.proceed(newRequest)
    }
}