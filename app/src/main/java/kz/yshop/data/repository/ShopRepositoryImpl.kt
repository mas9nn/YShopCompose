package kz.yshop.data.repository

import kz.yshop.data.remote.Util.checkResponse
import kz.yshop.data.remote.YShopApi
import kz.yshop.domain.repository.ShopRepository
import javax.inject.Inject


class ShopRepositoryImpl @Inject constructor(
    private val api: YShopApi
) : ShopRepository {

    override suspend fun getShop(id: String) = checkResponse(api.getShop(id = id))

}