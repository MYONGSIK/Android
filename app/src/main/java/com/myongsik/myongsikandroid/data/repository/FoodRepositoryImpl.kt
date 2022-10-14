package com.myongsik.myongsikandroid.data.repository

import com.myongsik.myongsikandroid.data.api.RetrofitInstance.api
import com.myongsik.myongsikandroid.data.model.SearchResponse
import retrofit2.Response

class FoodRepositoryImpl(

) : FoodRepository {
    override suspend fun searchBooks(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): Response<SearchResponse> {
        return api.searchBooks(query, sort, page, size)
    }
}