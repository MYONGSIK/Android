package com.myongsik.myongsikandroid.ui.viewmodel.food

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myongsik.myongsikandroid.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WidgetSettingViewModel @Inject constructor() : BaseViewModel() {

    val _dormitoryCheck = MutableLiveData<Boolean>()
    val dormitoryCheck: LiveData<Boolean> = _dormitoryCheck

    val _myongjinCheck = MutableLiveData<Boolean>()
    val myongjinCheck: LiveData<Boolean> = _myongjinCheck

    val _studentCheck = MutableLiveData<Boolean>()
    val studentCheck: LiveData<Boolean> = _studentCheck

    val _teacherCheck = MutableLiveData<Boolean>()
    val teacherCheck: LiveData<Boolean> = _teacherCheck
}