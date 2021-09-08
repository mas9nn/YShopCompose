package kz.yshop.data.repository

import kz.yshop.data.remote.Util.checkResponse
import kz.yshop.data.remote.YShopApi
import kz.yshop.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(private val api: YShopApi) : ProductRepository {

    override suspend fun getMainPageProducts(id: String) =
        checkResponse(api.getMainPageProducts(id = id))

    override suspend fun getProductDetail(id: String) = checkResponse(api.getProductDetail(id))
}