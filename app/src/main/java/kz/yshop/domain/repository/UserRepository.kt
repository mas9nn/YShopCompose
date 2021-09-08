package kz.yshop.domain.repository

import kz.yshop.data.remote.Util.Resource
import kz.yshop.data.remote.responses.Demo.UserDemo

interface UserRepository {

    suspend fun getDemoUser(
        deviceToken: String = ""
    ): Resource<UserDemo>
}