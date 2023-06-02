package com.myongsik.data.model.review

import com.myongsik.myongsikandroid.domain.model.food.ResponseReviewDataEntity

data class ResponseReviewData(
    val httpCode: Int,
    val success : Boolean,
    val localDateTime : String,
    val httpStatus : String,
    val message : String,
    val data : Result
){
    data class Result(
        val content: String,
        val createdAt : String,
        val reviewId : Int,
        val writerId : String,
        val updatedAt : String
    )
}

fun ResponseReviewData.toResponseReviewEntity() = ResponseReviewDataEntity(
    httpCode = this.httpCode,
    success = this.success,
    localDateTime = this.localDateTime,
    httpStatus = this.httpStatus,
    message = this.message,
    content = this.data.content,
    createdAt = this.data.createdAt,
    reviewId = this.data.reviewId,
    writerId = this.data.writerId,
    updatedAt = this.data.updatedAt
)

fun ResponseReviewDataEntity.toResponseReviewData() = ResponseReviewData(
    httpCode = this.httpCode,
    success = this.success,
    localDateTime = this.localDateTime,
    httpStatus = this.httpStatus,
    message = this.message,
    data = ResponseReviewData.Result(
        content = this.content,
        createdAt = this.createdAt,
        reviewId = this.reviewId,
        writerId = this.writerId,
        updatedAt = this.updatedAt
    )
)
