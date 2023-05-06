package com.myongsik.myongsikandroid.domain.usecase.love

import com.myongsik.myongsikandroid.data.repository.love.LoveRepository
import com.myongsik.myongsikandroid.domain.model.love.InsertFoodEntity
import javax.inject.Inject

class InsertFoodDataUseCase @Inject constructor(
    private val loveRepository : LoveRepository
) : LoveCase {

    suspend operator fun invoke(insertFoodEntity: InsertFoodEntity) =
        loveRepository.insertFood(insertFoodEntity)
}