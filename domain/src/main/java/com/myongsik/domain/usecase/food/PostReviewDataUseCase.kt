package com.myongsik.domain.usecase.food

import com.myongsik.domain.model.food.RequestReviewDataEntity
import com.myongsik.domain.repository.food.FoodV2Repository
import javax.inject.Inject

class PostReviewDataUseCase @Inject constructor(
    private val foodV2Repository: FoodV2Repository
){

    suspend operator fun invoke(requestReviewDataEntity: RequestReviewDataEntity)
        = foodV2Repository.postReview(requestReviewDataEntity)
}