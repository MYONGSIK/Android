package com.myongsik.myongsikandroid.data.repository.user

import com.myongsik.myongsikandroid.data.api.UserApi
import com.myongsik.myongsikandroid.data.model.user.RequestUserData
import com.myongsik.myongsikandroid.data.model.user.ResponseUserData
import com.myongsik.myongsikandroid.data.model.user.toRequestUserData
import com.myongsik.myongsikandroid.data.model.user.toResponseUserEntity
import com.myongsik.myongsikandroid.domain.model.RequestUserEntity
import com.myongsik.myongsikandroid.domain.model.ResponseUserEntity
import retrofit2.Response
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userApi: UserApi) : UserRepository {
    override suspend fun postUser(requestUserEntity: RequestUserEntity): ResponseUserEntity? {
        val response = userApi.postUser(requestUserEntity.toRequestUserData())
        return if (response.isSuccessful) {
            response.body()?.toResponseUserEntity()
        } else {
            null
        }
    }
}