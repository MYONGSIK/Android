package com.myongsik.data.datasource.food

import com.myongsik.domain.model.food.RequestReviewDataEntity
import com.myongsik.domain.model.food.ResponseReviewDataEntity
import com.myongsik.domain.model.food.ResponseWeekFoodEntity

interface FoodDataSource {

    suspend fun weekGetFoodArea(s: String): ResponseWeekFoodEntity?

    suspend fun postReview(requestReviewDataEntity: RequestReviewDataEntity): ResponseReviewDataEntity?

}