package com.myongsik.data.db

import androidx.paging.PagingSource
import androidx.room.*
import com.myongsik.data.model.kakao.Restaurant
import kotlinx.coroutines.flow.Flow

/*
장소를 찜해두는 DataBase
ROOM 사용
 */
@Dao
interface RestaurantDao {

    //좋아요
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoodFood(restaurant : Restaurant)

    //Room 관심목록 페이징변환
    @Query("SELECT * FROM love_list")
    fun getFoods() : PagingSource<Int, Restaurant>

    @Query("SELECT * FROM love_list")
    fun getIsLoveFood() : Flow<List<Restaurant>>

    //관심목록에 담기를 클릭했을 때 이미 있는지 판단하는 쿼리
    @Query("SELECT * FROM love_list WHERE id = :id")
    suspend fun loveIs(id : String) : Restaurant

    //찜꽁 리스트에서 삭제하는 쿼리
    @Delete
    suspend fun deleteBook(restaurant: Restaurant)

    @Query("select exists(SELECT * FROM love_list WHERE id = :id)")
    fun loveUpdate(id : String) : Boolean
}