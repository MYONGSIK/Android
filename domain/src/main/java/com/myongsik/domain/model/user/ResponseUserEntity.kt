package com.myongsik.domain.model.user

data class ResponseUserEntity(
    val httpCode: Int,
    val success: Boolean,
    val localDataTime: ArrayList<Int>,
    val httpStatus: String,
    val message: String,
    val id: String,
    val phoneId: String
)

