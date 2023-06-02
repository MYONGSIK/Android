package com.myongsik.myongsikandroid.domain.usecase.user

import com.myongsik.myongsikandroid.domain.repository.user.UserRepository
import com.myongsik.myongsikandroid.domain.model.user.RequestUserEntity
import javax.inject.Inject

class PostUserDataUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(requestUserEntity: RequestUserEntity) = userRepository.postUser(requestUserEntity)
}