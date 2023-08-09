package com.myongsik.myongsikandroid.data.datasource.food

import com.myongsik.myongsikandroid.base.autoHandleApiResponse
import com.myongsik.myongsikandroid.data.api.HomeFoodApi
import com.myongsik.myongsikandroid.data.model.food.toWeekFoodEntity
import com.myongsik.myongsikandroid.data.model.review.toRequestReviewData
import com.myongsik.myongsikandroid.data.model.review.toResponseReviewEntity
import com.myongsik.myongsikandroid.domain.model.food.RequestReviewDataEntity
import com.myongsik.myongsikandroid.domain.model.food.ResponseReviewDataEntity
import com.myongsik.myongsikandroid.domain.model.food.ResponseWeekFoodEntity
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FoodDataSourceImpl @Inject constructor(
    private val homeFoodApi : HomeFoodApi
) : FoodDataSource {

    override suspend fun weekGetFoodArea(s: String) = autoHandleApiResponse({ it.toWeekFoodEntity() }) {
        homeFoodApi.weekGetFoodArea(s)
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