package com.myongsik.myongsikandroid.data.repository.user

import com.myongsik.myongsikandroid.domain.model.RequestUserEntity
import com.myongsik.myongsikandroid.domain.model.ResponseUserEntity

interface UserRepository {

    suspend fun postUser(requestUserEntity: RequestUserEntity): ResponseUserEntity?

}