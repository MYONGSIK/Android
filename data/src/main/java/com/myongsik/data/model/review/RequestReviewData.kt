package com.myongsik.data.model.review

import com.myongsik.myongsikandroid.domain.model.food.RequestReviewDataEntity


data class RequestReviewData(
    var content : String,
    val registeredAt : String,
    var writerId : String,
    val areaName : String
)

fun RequestReviewData.toRequestReviewEntity() = RequestReviewDataEntity(
    content = this.content,
    registeredAt = this.registeredAt,
    writerId = this.writerId,
    areaName = this.areaName
)

fun RequestReviewDataEntity.toRequestReviewData() = RequestReviewData(
    content = this.content,
    registeredAt = this.registeredAt,
    writerId = this.writerId,
    areaName = this.areaName
)
