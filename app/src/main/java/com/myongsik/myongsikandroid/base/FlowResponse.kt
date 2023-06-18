package com.myongsik.myongsikandroid.base

import com.myongsik.myongsikandroid.util.ErrorConstant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.net.SocketException

fun <T> safeApiCall(call: suspend () -> Response<T>): Flow<BaseResult<T>> = flow {
    try {
        val response = call()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            emit(BaseResult.Success(body))
        } else {
            emit(BaseResult.Error(response.code()))
        }
    } catch (e: Exception) {
        when (e) {
            is SocketException -> emit(BaseResult.Error(ErrorConstant.EVENT_SOCKET_EXCEPTION))
            is HttpException -> emit(BaseResult.Error(ErrorConstant.EVENT_HTTP_EXCEPTION))
            else -> emit(BaseResult.Error(ErrorConstant.EVENT_UNKNOWN_HOST_EXCEPTION))
        }
    }
}