package com.myongsik.myongsikandroid.data.datasource.user

import com.myongsik.myongsikandroid.domain.model.user.RequestUserEntity
import com.myongsik.myongsikandroid.domain.model.user.ResponseUserEntity

interface UserDataSource {

    suspend fun postUser(requestUserEntity: RequestUserEntity): ResponseUserEntity?
}