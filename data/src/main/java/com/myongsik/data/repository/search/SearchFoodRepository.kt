package com.myongsik.data.repository.search

import androidx.paging.PagingData
import com.myongsik.data.model.kakao.Restaurant
import com.myongsik.data.model.kakao.SearchResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface SearchFoodRepository {

    //카카오 오픈 API 장소 검색
    suspend fun searchFood(
        query: String,
        category_group_code : String,
        x : String,
        y : String,
        radius : Int,
        page : Int,
        size : Int,
        sort : String,
    ) : Response<SearchResponse>

    //장소 검색 -> 페이징
    fun searchPagingFood(query : String) : Flow<PagingData<Restaurant>>

}