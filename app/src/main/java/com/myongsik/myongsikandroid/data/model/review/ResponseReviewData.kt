package com.myongsik.myongsikandroid.data.model.review

data class ResponseReviewData(
    val httpCode: Int,
    val success : Boolean,
    val localDataTime : ArrayList<Int>,
    val httpStatus : String,
    val message : String,
    val data : Result
){
    data class Result(
        val content: String,
        val createdAt : String,
        val reviewId : Long,
        val writerId : String
    )
}

