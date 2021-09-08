package kz.yshop.domain.repository

import kz.yshop.data.remote.Util.Resource
import kz.yshop.data.remote.responses.ShopAbout.Shop

interface ShopRepository {

    suspend fun getShop(id: String = "448"):Resource<Shop>
}