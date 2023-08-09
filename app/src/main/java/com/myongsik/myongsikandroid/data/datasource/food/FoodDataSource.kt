package com.myongsik.myongsikandroid.data.datasource.food

import com.myongsik.myongsikandroid.base.BaseResult
import com.myongsik.myongsikandroid.domain.model.food.RequestReviewDataEntity
import com.myongsik.myongsikandroid.domain.model.food.ResponseReviewDataEntity
import com.myongsik.myongsikandroid.domain.model.food.ResponseWeekFoodEntity
import kotlinx.coroutines.flow.Flow

interface FoodDataSource {

    suspend fun weekGetFoodArea(s: String): Flow<BaseResult<ResponseWeekFoodEntity>>

    suspend fun postReview(requestReviewDataEntity: RequestReviewDataEntity): ResponseReviewDataEntity?

}