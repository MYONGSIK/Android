package com.myongsik.domain.repository.user

import com.myongsik.domain.model.user.RequestUserEntity
import com.myongsik.domain.model.user.ResponseUserEntity

interface UserRepository {

    suspend fun postUser(requestUserEntity: RequestUserEntity): ResponseUserEntity?

}