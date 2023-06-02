package com.myongsik.presentation.viewmodel.food

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.myongsik.data.model.user.RequestUserData
import com.myongsik.data.model.user.ResponseUserData
import com.myongsik.data.model.user.toRequestUserEntity
import com.myongsik.data.model.user.toResponseUserData
import com.myongsik.domain.usecase.user.PostUserDataUseCase
import com.myongsik.myongsikandroid.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val postUserDataUseCase: PostUserDataUseCase
) : BaseViewModel() {

    private val _postUserData = MutableLiveData<ResponseUserData>()
    val postUserData: LiveData<ResponseUserData>
        get() = _postUserData

    fun postUser(requestUserData: RequestUserData) = launch {
        postUserDataUseCase(requestUserData.toRequestUserEntity())?.let {
            _postUserData.postValue(it.toResponseUserData())
        }
    }
}