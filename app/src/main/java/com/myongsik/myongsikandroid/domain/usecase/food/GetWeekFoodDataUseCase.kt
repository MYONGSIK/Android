package com.myongsik.myongsikandroid.domain.usecase.food

import com.myongsik.myongsikandroid.domain.repository.food.FoodV2Repository
import javax.inject.Inject

class GetWeekFoodDataUseCase @Inject constructor(
    private val foodV2Repository: FoodV2Repository
){
    suspend operator fun invoke(s : String) = foodV2Repository.weekGetFoodArea(s)
}