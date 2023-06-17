package com.myongsik.domain.model.food

data class ResponseReviewDataEntity(
    val httpCode: Int,
    val success : Boolean,
    val localDateTime : String,
    val httpStatus : String,
    val message : String,
    val content: String,
    val createdAt : String,
    val reviewId : Int,
    val writerId : String,
    val updatedAt : String
)

