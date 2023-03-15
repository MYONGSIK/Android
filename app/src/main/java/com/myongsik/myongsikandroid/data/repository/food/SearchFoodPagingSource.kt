package com.myongsik.myongsikandroid.data.repository.food

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.myongsik.myongsikandroid.data.api.SearchFoodApi
import com.myongsik.myongsikandroid.data.model.kakao.Restaurant
import com.myongsik.myongsikandroid.util.Constant.PAGING_SIZE
import com.myongsik.myongsikandroid.util.MyongsikApplication
import retrofit2.HttpException
import java.io.IOException

//Paging Source
class SearchFoodPagingSource(
    private val query: String,
    private val searchFoodApi: SearchFoodApi
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
            if (MyongsikApplication.prefs.getUserCampus() == "S") {
                c = "서울"
                x = 126.923460283882
                y = 37.5803504797164
            } else if (MyongsikApplication.prefs.getUserCampus() == "Y") {
                c = "용인"
                x = 127.18758354347
                y = 37.224650469991
            }
            val response = searchFoodApi.searchFood(
                "$c 명지대 $query", "FD6, CE7", "$x",
                "$y", 1500, pageNumber, params.loadSize, "distance"
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