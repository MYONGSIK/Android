package com.myongsik.myongsikandroid.data.model.user

import com.myongsik.myongsikandroid.domain.model.RequestUserEntity


data class RequestUserData(
    var phoneId: String
)

fun RequestUserData.toRequestUserEntity() = RequestUserEntity(
    phoneId = this.phoneId
)

fun RequestUserEntity.toRequestUserData() = RequestUserData(
    phoneId = this.phoneId
)

