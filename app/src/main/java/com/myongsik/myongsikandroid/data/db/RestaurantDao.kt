package com.myongsik.myongsikandroid.data.db

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import com.myongsik.myongsikandroid.data.model.food.FoodResult
import com.myongsik.myongsikandroid.data.model.kakao.Restaurant
import kotlinx.coroutines.flow.Flow

/*
10/17일 현재 사용되지 않음
맛있어요, 맛없어요를 Room 을 이용할지, DataStore 를 이용해야할지 고민.
 */
@Dao
interface RestaurantDao {

    //좋아요
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoodFood(restaurant : Restaurant)

    @Query("SELECT * FROM love_list")
    fun getFoods() : PagingSource<Int, Restaurant>

    @Update
    suspend fun deleteBook(restaurant: Restaurant)


}