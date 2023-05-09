package com.myongsik.myongsikandroid.data.datasource.user

import com.myongsik.myongsikandroid.data.api.UserApi
import com.myongsik.myongsikandroid.data.model.user.toRequestUserData
import com.myongsik.myongsikandroid.data.model.user.toResponseUserEntity
import com.myongsik.myongsikandroid.domain.model.user.RequestUserEntity
import com.myongsik.myongsikandroid.domain.model.user.ResponseUserEntity
import javax.inject.Inject

class UserDataSourceImpl  @Inject constructor(
    private val userApi: UserApi
) : UserDataSource {

    override suspend fun postUser(requestUserEntity: RequestUserEntity): ResponseUserEntity? {
        val response = userApi.postUser(requestUserEntity.toRequestUserData())
        return if (response.isSuccessful) {
            response.body()?.toResponseUserEntity()
        } else {
            null
        }
    }
}