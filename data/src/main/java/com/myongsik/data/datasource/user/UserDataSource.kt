package com.myongsik.data.datasource.user

import com.myongsik.domain.model.user.RequestUserEntity
import com.myongsik.domain.model.user.ResponseUserEntity

interface UserDataSource {

    suspend fun postUser(requestUserEntity: RequestUserEntity): ResponseUserEntity?
}