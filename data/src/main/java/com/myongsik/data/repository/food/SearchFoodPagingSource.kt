package com.myongsik.data.repository.food

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.myongsik.data.api.SearchFoodApi
import com.myongsik.data.model.kakao.Restaurant
import com.myongsik.data.util.Constant
import com.myongsik.data.util.Constant.PAGING_SIZE
import retrofit2.HttpException
import java.io.IOException

//Paging Source
class SearchFoodPagingSource(
    private val query: String,
    private val searchFoodApi: SearchFoodApi,
    private val userCampus: String
) : PagingSource<Int, Restaurant>() {

    override fun getRefreshKey(state: PagingState<Int, Restaurant>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Restaurant> {
        return try {
            val pageNumber = params.key ?: STARTING_PAGE_INDEX
            var c: String = ""
            var x: Double = 0.0
            var y: Double = 0.0
            var radius : Int = 1500
            if (userCampus == Constant.S) {
                c = Constant.SEOUL_CAMPUS
                x = Constant.SEOUL_CAMPUS_X
                y = Constant.SEOUL_CAMPUS_Y
                radius = 1500
            } else if (userCampus == Constant.Y) {
                c = Constant.YONGIN_CAMPUS
                x = Constant.YONGIN_CAMPUS_X
                y = Constant.YONGIN_CAMPUS_Y
                radius = 3000
            }
            val response = searchFoodApi.searchFood(
                "$c $query", Constant.CATEGORY_GROUP_CODE, "$x",
                "$y", radius, pageNumber, params.loadSize, Constant.DISTANCE
            )
            val endOfPaginationReached = response.body()?.meta?.is_end!!

            val data = response.body()?.documents!!

            val prevKey = if (pageNumber == STARTING_PAGE_INDEX) null else pageNumber - 1
            val nextKey = if (endOfPaginationReached) {
                null
            } else {
                pageNumber + (params.loadSize / PAGING_SIZE)
            }

            LoadResult.Page(
                data = data,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    companion object {
        const val STARTING_PAGE_INDEX = 1
    }
}