package com.myongsik.myongsikandroid.data.repository

import com.myongsik.myongsikandroid.data.model.SearchResponse
import retrofit2.Response

interface FoodRepository {

    suspend fun searchBooks(
        query : String,
        sort : String,
        page : Int,
        size : Int,
    ) : Response<SearchResponse>
}