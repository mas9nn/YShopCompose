package kz.yshop.data.remote

import kz.yshop.data.remote.responses.Demo.UserDemo
import kz.yshop.data.remote.responses.MainPageProducts.MainPageProducts
import kz.yshop.data.remote.responses.ProductDetail.ProductDetail
import kz.yshop.data.remote.responses.ShopAbout.Shop
import retrofit2.Response
import retrofit2.http.*

interface YShopApi {

    @FormUrlEncoded
    @POST("demo")
    suspend fun getDemoUser(
        @Field("device_token") deviceToken: String = ""
    ): Response<UserDemo>

    @GET("shop-about")
    suspend fun getShop(
        @Query("id_shop") id: String = "448"
    ): Response<Shop>

    @GET("main-page-products")
    suspend fun getMainPageProducts(
        @Query("id_shop") id: String = "448"
    ): Response<MainPageProducts>

    @GET("product-detail")
    suspend fun getProductDetail(
        @Query("id_product") id: String
    ): Response<ProductDetail>


}