package com.myongsik.myongsikandroid.ui.viewmodel.food

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.myongsik.myongsikandroid.data.model.user.RequestUserData
import com.myongsik.myongsikandroid.data.model.user.ResponseUserData
import com.myongsik.myongsikandroid.data.repository.food.FoodRepository
import com.myongsik.myongsikandroid.ui.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val foodRepository: FoodRepository
) : BaseViewModel() {

    private val _postUserData = MutableLiveData<ResponseUserData>()
    val postUserData: LiveData<ResponseUserData>
        get() = _postUserData

    fun postUser(requestUserData: RequestUserData) = launch {
        val response = foodRepository.postUser(requestUserData)

        if (response.code() == 200) {
            _postUserData.postValue(response.body())
        }
    }
}