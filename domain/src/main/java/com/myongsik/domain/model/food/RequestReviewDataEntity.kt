package com.myongsik.domain.model.food


data class RequestReviewDataEntity(
    var content : String,
    val registeredAt : String,
    var writerId : String,
    val areaName : String
)
