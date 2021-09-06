package kz.yshop.repository

import android.util.Log
import androidx.lifecycle.LiveData
import dagger.hilt.android.scopes.ActivityScoped
import kz.yshop.data.db.YShopDatabase
import kz.yshop.data.remote.Util.Resource
import kz.yshop.data.remote.YShopApi
import kz.yshop.data.remote.responses.Demo.Data
import retrofit2.Response
import javax.inject.Inject


@ActivityScoped
class ShopRepository @Inject constructor(
    private val api: YShopApi,
    private val database: YShopDatabase
) {

    suspend fun getDemoUser(deviceToken: String = "") =
        checkResponse(api.getDemoUser(deviceToken = deviceToken))


    suspend fun getShop(id: String = "443") = checkResponse(api.getShop(id = id))

    suspend fun getMainPageProducts(id: String = "443") =

        checkResponse(api.getMainPageProducts(id = id))

    suspend fun getProductDetail(id: String) =
        checkResponse(api.getProductDetail(id = id))

    private fun <T : Any> checkResponse(data: Response<T>): Resource<T> {
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
}