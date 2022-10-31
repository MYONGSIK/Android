package com.myongsik.myongsikandroid.data.repository.search

import androidx.paging.PagingData
import com.myongsik.myongsikandroid.data.model.food.FoodResult
import com.myongsik.myongsikandroid.data.model.food.TodayFoodResponse
import com.myongsik.myongsikandroid.data.model.food.WeekFoodResponse
import com.myongsik.myongsikandroid.data.model.kakao.Restaurant
import com.myongsik.myongsikandroid.data.model.kakao.SearchResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

/*
Repository 패턴을 이용한 FoodRepository interface
 */
interface SearchFoodRepository {

    suspend fun searchFood(
        query: String,
        category_group_code : String,
        x : String,
        y : String,
        radius : Int,
        page : Int,
        size : Int,
    ) : Response<SearchResponse>

}