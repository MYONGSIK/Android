package com.myongsik.data.model.user

import com.myongsik.domain.model.user.RequestUserEntity


data class RequestUserData(
    var phoneId: String
)

fun RequestUserData.toRequestUserEntity() = RequestUserEntity(
    phoneId = this.phoneId
)

fun RequestUserEntity.toRequestUserData() = RequestUserData(
    phoneId = this.phoneId
)

