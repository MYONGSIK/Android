package com.myongsik.data.repository.food

import com.myongsik.data.datasource.food.FoodDataSource
import com.myongsik.domain.model.food.RequestReviewDataEntity
import com.myongsik.domain.model.food.ResponseReviewDataEntity
import com.myongsik.domain.model.food.ResponseWeekFoodEntity
import com.myongsik.domain.repository.food.FoodV2Repository
import javax.inject.Inject

class FoodV2RepositoryImpl @Inject constructor(
    private val foodDataSource: FoodDataSource
) : FoodV2Repository {

    override suspend fun weekGetFoodArea(s: String): ResponseWeekFoodEntity? {
        return foodDataSource.weekGetFoodArea(s)
    }

    override suspend fun postReview(requestReviewDataEntity: RequestReviewDataEntity): ResponseReviewDataEntity? {
        return foodDataSource.postReview(requestReviewDataEntity)
    }
}