package com.myongsik.myongsikandroid.data.model.review

//회원가입

data class RequestReviewData(
    var content : String,
    val registeredAt : String,
    var writerId : String
)
