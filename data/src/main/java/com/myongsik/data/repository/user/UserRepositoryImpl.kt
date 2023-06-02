package com.myongsik.data.repository.user

import com.myongsik.data.datasource.user.UserDataSource
import com.myongsik.domain.model.user.RequestUserEntity
import com.myongsik.domain.model.user.ResponseUserEntity
import com.myongsik.domain.repository.user.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource
) : UserRepository {

    override suspend fun postUser(requestUserEntity: RequestUserEntity): ResponseUserEntity? {
        return userDataSource.postUser(requestUserEntity)
    }
}