package com.myongsik.domain.repository.food

import com.myongsik.domain.model.food.RequestReviewDataEntity
import com.myongsik.domain.model.food.ResponseReviewDataEntity
import com.myongsik.domain.model.food.ResponseWeekFoodEntity


interface FoodV2Repository {

    suspend fun weekGetFoodArea(s: String): ResponseWeekFoodEntity?

    suspend fun postReview(requestReviewDataEntity: RequestReviewDataEntity): ResponseReviewDataEntity?
}