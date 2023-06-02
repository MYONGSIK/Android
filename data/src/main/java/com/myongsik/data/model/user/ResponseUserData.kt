package com.myongsik.data.model.user

import com.myongsik.domain.model.user.ResponseUserEntity

data class ResponseUserData(
    val httpCode: Int,
    val success: Boolean,
    val localDataTime: ArrayList<Int>,
    val httpStatus: String,
    val message: String,
    val data: Result
) {
    data class Result(
        val id: String,
        val phoneId: String
    )
}

fun ResponseUserData.toResponseUserEntity() = ResponseUserEntity(
    httpCode = this.httpCode,
    success = this.success,
    localDataTime = this.localDataTime,
    httpStatus = this.httpStatus,
    message = this.message,
    id = data.id,
    phoneId = data.phoneId
)

fun ResponseUserEntity.toResponseUserData() = ResponseUserData(
    httpCode = this.httpCode,
    success = this.success,
    localDataTime = this.localDataTime,
    httpStatus = this.httpStatus,
    message = this.message,
    data = ResponseUserData.Result(
        id = this.id,
        phoneId = this.phoneId
    )
)

