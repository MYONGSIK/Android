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

    //Room 관심목록 페이징변환
    @Query("SELECT * FROM love_list")
    fun getFoods() : PagingSource<Int, Restaurant>

    //관심목록에 담기를 클릭했을 때 이미 있는지 판단하는 쿼리
    @Query("SELECT * FROM love_list WHERE id = :id")
    fun loveIs(id : String) : Restaurant

    @Delete
    suspend fun deleteBook(restaurant: Restaurant)



}