package com.myongsik.myongsikandroid.data.repository.food

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.myongsik.myongsikandroid.data.api.SearchFoodApi
import com.myongsik.myongsikandroid.data.model.kakao.Restaurant
import com.myongsik.myongsikandroid.util.Constant.PAGING_SIZE
import retrofit2.HttpException
import java.io.IOException


class SearchFoodPagingSource(
    private val query : String,
) : PagingSource<Int, Restaurant>(){

    override fun getRefreshKey(state: PagingState<Int, Restaurant>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Restaurant> {
        return try{
            val pageNumber = params.key ?: STARTING_PAGE_INDEX
            val response = SearchFoodApi.create().searchFood(
                "서울 명지대 $query", "FD6, CE7", "126.923460283882",
                "37.5803504797164", 1500, pageNumber, params.loadSize, "distance"
            )
            val endOfPaginationReached = response.body()?.meta?.is_end!!

//            val lastPage = response.body()?.meta?.total_count?.div(15)

            val data = response.body()?.documents!!

            val prevKey = if(pageNumber == STARTING_PAGE_INDEX) null else pageNumber - 1
            val nextKey = if(endOfPaginationReached) {
                null
            }else{
                pageNumber + (params.loadSize / PAGING_SIZE)
            }

//            val prevKey = if(pageNumber == STARTING_PAGE_INDEX) null else pageNumber - 1
//            val nextKey = if(lastPage == pageNumber) {
//                null
//            }else{
//                pageNumber + (params.loadSize / PAGING_SIZE)
//            }

            LoadResult.Page(
                data = data,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (exception : IOException){
            LoadResult.Error(exception)
        } catch (exception : HttpException){
            LoadResult.Error(exception)
        }
    }

    companion object {
        const val STARTING_PAGE_INDEX = 1
    }
}