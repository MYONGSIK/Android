package com.myongsik.myongsikandroid.data.model.review


data class RequestReviewData(
    var content : String,
    val registeredAt : String,
    var writerId : String,
    val areaName : String
)
