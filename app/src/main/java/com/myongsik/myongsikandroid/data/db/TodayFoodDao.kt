package com.myongsik.myongsikandroid.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.myongsik.myongsikandroid.data.model.FoodResult

@Dao
interface TodayFoodDao {

    //좋아요
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoodFood(foodResult: FoodResult)

    @Query("SELECT * FROM food WHERE classification = :classification AND today = :today")
    fun getFood(classification : String, today : String) : LiveData<FoodResult>

    @Update
    suspend fun deleteBook(foodResult: FoodResult)


}