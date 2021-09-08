package kz.yshop.data.repository

import kz.yshop.data.remote.Util.checkResponse
import kz.yshop.data.remote.YShopApi
import kz.yshop.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: YShopApi
) : UserRepository {

    override suspend fun getDemoUser(deviceToken: String) = checkResponse(api.getDemoUser(deviceToken))
}