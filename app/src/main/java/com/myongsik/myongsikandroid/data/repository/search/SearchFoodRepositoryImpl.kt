package com.myongsik.myongsikandroid.data.repository.search


import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.myongsik.myongsikandroid.data.api.SearchFoodApi
import com.myongsik.myongsikandroid.data.model.kakao.Restaurant
import com.myongsik.myongsikandroid.data.model.kakao.SearchResponse
import com.myongsik.myongsikandroid.data.repository.food.SearchFoodPagingSource
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
        size: Int,
        sort : String
    ): Response<SearchResponse> {
        return api.searchFood(query, category_group_code, x, y, radius, page, size, sort)
    }

    override fun searchPagingFood(query: String): Flow<PagingData<Restaurant>> {
        val pagingSourceFactory = { SearchFoodPagingSource(query)}

        return Pager(
            config = PagingConfig(
                //첫 로딩 페이지는 pageSize * 3이므로, 첫 로딩 페이지를 15로 설정함.
                //size 의 최댓값이 15이기 때문에
                initialLoadSize = PAGING_SIZE, //첫 페이지는 pageSize 의 3배 따라서 이와 같이 설정해줌
                pageSize = PAGING_SIZE, //15
                enablePlaceholders = false,
                maxSize = PAGING_SIZE * 3 //15 * 3
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }


}