package com.myongsik.myongsikandroid.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.myongsik.myongsikandroid.data.model.food.FoodResult

/*
10/17일 현재 사용되지 않음
맛있어요, 맛없어요를 Room 을 이용할지, DataStore 를 이용해야할지 고민.
 */
@Dao
interface RestaurantDao {

    //좋아요
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoodFood(foodResult: FoodResult)

    @Query("SELECT * FROM food WHERE classification = :classification AND today = :today")
    fun getFood(classification : String, today : String) : LiveData<FoodResult>

    @Update
    suspend fun deleteBook(foodResult: FoodResult)


}