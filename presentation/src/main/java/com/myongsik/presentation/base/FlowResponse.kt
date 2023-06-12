package com.myongsik.myongsikandroid.base

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

fun <T> safeApiCall(call: suspend () -> Response<T>): Flow<ApiResponse<T>> = flow {
    emit(ApiResponse.Loading)

    try {
        val response = call()
        if (response.isSuccessful) {
            emit(ApiResponse.Success(response.body()!!))
        } else {
            emit(ApiResponse.Failure(response.code().toString()))
        }
    }  catch (e: Exception) {
        e.printStackTrace()
        emit(ApiResponse.Failure(e.message ?: e.toString()))
    }
}