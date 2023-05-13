package com.myongsik.myongsikandroid.base

sealed class ApiResponse<out T> {
    object Loading: ApiResponse<Nothing>()

    data class Success<out T>(
        val data: T
    ): ApiResponse<T>()

    data class Failure(
        val code: String,
    ): ApiResponse<Nothing>()
}
