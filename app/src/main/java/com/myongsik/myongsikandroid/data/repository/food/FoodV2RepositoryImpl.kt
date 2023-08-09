package com.myongsik.myongsikandroid.data.repository.food

import android.util.Log
import com.myongsik.myongsikandroid.base.BaseResult
import com.myongsik.myongsikandroid.data.datasource.food.FoodDataSource
import com.myongsik.myongsikandroid.domain.model.food.RequestReviewDataEntity
import com.myongsik.myongsikandroid.domain.model.food.ResponseReviewDataEntity
import com.myongsik.myongsikandroid.domain.model.food.ResponseWeekFoodEntity
import com.myongsik.myongsikandroid.domain.repository.food.FoodV2Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FoodV2RepositoryImpl @Inject constructor(
    private val foodDataSource: FoodDataSource
) : FoodV2Repository {

    override suspend fun weekGetFoodArea(s: String): Flow<BaseResult<ResponseWeekFoodEntity>> {
        return foodDataSource.weekGetFoodArea(s)
    }

    override suspend fun postReview(requestReviewDataEntity: RequestReviewDataEntity): ResponseReviewDataEntity? {
        return foodDataSource.postReview(requestReviewDataEntity)
    }
}