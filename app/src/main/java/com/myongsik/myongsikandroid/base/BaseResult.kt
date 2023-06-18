package com.myongsik.myongsikandroid.base

sealed class BaseResult<out T> {

    data class Success<out T>(val data: T) : BaseResult<T>()

    data class Error(val errorCode: Int) : BaseResult<Nothing>()
}
