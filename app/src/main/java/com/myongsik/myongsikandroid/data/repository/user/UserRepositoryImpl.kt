package com.myongsik.myongsikandroid.data.repository.user

import com.myongsik.myongsikandroid.data.api.UserApi
import com.myongsik.myongsikandroid.data.model.user.toRequestUserData
import com.myongsik.myongsikandroid.data.model.user.toResponseUserEntity
import com.myongsik.myongsikandroid.domain.model.user.RequestUserEntity
import com.myongsik.myongsikandroid.domain.model.user.ResponseUserEntity
import com.myongsik.myongsikandroid.domain.repository.user.UserRepository
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