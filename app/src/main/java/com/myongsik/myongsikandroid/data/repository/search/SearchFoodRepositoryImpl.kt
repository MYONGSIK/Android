package com.myongsik.myongsikandroid.data.repository.search


import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.myongsik.myongsikandroid.data.api.SearchFoodApi
import com.myongsik.myongsikandroid.data.db.RestaurantDatabase
import com.myongsik.myongsikandroid.data.model.kakao.Restaurant
import com.myongsik.myongsikandroid.data.model.kakao.SearchResponse
import com.myongsik.myongsikandroid.util.Constant.PAGING_SIZE
import kotlinx.coroutines.flow.Flow
import retrofit2.Response


class SearchFoodRepositoryImpl(
    private val api : SearchFoodApi,
) : SearchFoodRepository {

    override suspend fun searchFood(
        query: String,
        category_group_code: String,
        x: String,
        y: String,
        radius: Int,
        page: Int,
        size: Int
    ): Response<SearchResponse> {
        return api.searchFood(query, category_group_code, x, y, radius, page, size)
    }


}