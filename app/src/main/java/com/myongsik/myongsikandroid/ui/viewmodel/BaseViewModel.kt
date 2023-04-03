package com.myongsik.myongsikandroid.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myongsik.myongsikandroid.util.ErrorConstant
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.net.SocketException
import java.net.UnknownHostException

abstract class BaseViewModel: ViewModel() {

    private val _exceptionLiveData = MutableLiveData<Int>()
    val exceptionLiveData: LiveData<Int> get() = _exceptionLiveData

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(throwable.message, throwable.toString())
        when (throwable) {
            is SocketException -> _exceptionLiveData.postValue(ErrorConstant.EVENT_SOCKET_EXCEPTION) // Bad Internet
            is HttpException -> _exceptionLiveData.postValue(ErrorConstant.EVENT_HTTP_EXCEPTION) // Parse Error
            is UnknownHostException -> _exceptionLiveData.postValue(ErrorConstant.EVENT_UNKNOWN_HOST_EXCEPTION) // Wrong connection
            else -> _exceptionLiveData.postValue(ErrorConstant.EVENT_COROUTINE_EXCEPTION)
        }
    }

    fun ViewModel.launch(dispatcher: CoroutineDispatcher = Dispatchers.IO, block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(dispatcher + exceptionHandler) {
            block()
        }
    }
}