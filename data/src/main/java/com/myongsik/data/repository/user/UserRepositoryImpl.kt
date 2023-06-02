package com.myongsik.data.repository.user

import com.myongsik.myongsikandroid.data.datasource.user.UserDataSource
import com.myongsik.myongsikandroid.domain.model.user.RequestUserEntity
import com.myongsik.myongsikandroid.domain.model.user.ResponseUserEntity
import com.myongsik.myongsikandroid.domain.repository.user.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource
) : UserRepository {

    override suspend fun postUser(requestUserEntity: RequestUserEntity): ResponseUserEntity? {
        return userDataSource.postUser(requestUserEntity)
    }
}