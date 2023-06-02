package com.myongsik.data.datasource.food

import com.myongsik.data.model.food.toWeekFoodEntity
import com.myongsik.data.model.review.toRequestReviewData
import com.myongsik.data.model.review.toResponseReviewEntity
import com.myongsik.domain.model.food.RequestReviewDataEntity
import com.myongsik.domain.model.food.ResponseReviewDataEntity
import com.myongsik.domain.model.food.ResponseWeekFoodEntity
import javax.inject.Inject

class FoodDataSourceImpl @Inject constructor(
    private val homeFoodApi : com.myongsik.data.api.HomeFoodApi
) : FoodDataSource {

    override suspend fun weekGetFoodArea(s: String): ResponseWeekFoodEntity? {
        val response = homeFoodApi.weekGetFoodArea(s)
        return if(response.isSuccessful) {
            response.body()?.toWeekFoodEntity()
        } else {
            null
        }
    }

    override suspend fun postReview(requestReviewDataEntity: RequestReviewDataEntity): ResponseReviewDataEntity? {
        val response = homeFoodApi.postReview(requestReviewDataEntity.toRequestReviewData())
        return if(response.isSuccessful) {
            response.body()?.toResponseReviewEntity()
        } else {
            null
        }
    }
}