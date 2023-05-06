package com.myongsik.myongsikandroid.data.repository.user

import com.myongsik.myongsikandroid.domain.model.user.RequestUserEntity
import com.myongsik.myongsikandroid.domain.model.user.ResponseUserEntity

interface UserRepository {

    suspend fun postUser(requestUserEntity: RequestUserEntity): ResponseUserEntity?

}