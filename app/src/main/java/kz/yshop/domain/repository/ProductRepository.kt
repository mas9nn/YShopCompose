package kz.yshop.domain.repository

import kz.yshop.data.remote.Util.Resource
import kz.yshop.data.remote.responses.MainPageProducts.MainPageProducts
import kz.yshop.data.remote.responses.ProductDetail.ProductDetail

interface ProductRepository {

    suspend fun getMainPageProducts(
        id: String = "448"
    ): Resource<MainPageProducts>

    suspend fun getProductDetail(
        id: String
    ): Resource<ProductDetail>

}