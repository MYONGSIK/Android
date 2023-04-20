package com.myongsik.myongsikandroid.ui.viewmodel.food

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.myongsik.myongsikandroid.BaseViewModel
import com.myongsik.myongsikandroid.data.repository.food.FoodRepository
import com.myongsik.myongsikandroid.data.type.WidgetType
import com.myongsik.myongsikandroid.data.type.toWidgetType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WidgetSettingViewModel @Inject constructor(private val foodRepository: FoodRepository) : BaseViewModel() {

    private val _dormitoryCheck = MutableLiveData<Boolean>()
    val dormitoryCheck: LiveData<Boolean> = _dormitoryCheck

    private val _myongjinCheck = MutableLiveData<Boolean>()
    val myongjinCheck: LiveData<Boolean> = _myongjinCheck

    private val _studentCheck = MutableLiveData<Boolean>()
    val studentCheck: LiveData<Boolean> = _studentCheck

    private val _teacherCheck = MutableLiveData<Boolean>()
    val teacherCheck: LiveData<Boolean> = _teacherCheck

    private fun setWidgetType(type: String) {
        viewModelScope.launch {
            foodRepository.saveWidgetType(type)
        }
    }

    fun getCheckData() {
        viewModelScope.launch {
            foodRepository.getCurrentWidgetType().collectLatest {
                when(it.toWidgetType()) {
                    WidgetType.DORMITORY -> checkDormitory()
                    WidgetType.MYONGJIN -> checkMyongjin()
                    WidgetType.STUDENT -> checkStudent()
                    WidgetType.TEACHER -> checkTeacher()
                }
            }
        }
    }

    fun checkDormitory() {
        _dormitoryCheck.value = true
        _myongjinCheck.value = false
        _studentCheck.value = false
        _teacherCheck.value = false
        setWidgetType(WidgetType.DORMITORY.name)
    }
    fun checkMyongjin() {
        _dormitoryCheck.value = false
        _myongjinCheck.value = true
        _studentCheck.value = false
        _teacherCheck.value = false
        setWidgetType(WidgetType.MYONGJIN.name)
    }

    fun checkStudent() {
        _dormitoryCheck.value = false
        _myongjinCheck.value = false
        _studentCheck.value = true
        _teacherCheck.value = false
        setWidgetType(WidgetType.STUDENT.name)
    }

    fun checkTeacher() {
        _dormitoryCheck.value = false
        _myongjinCheck.value = false
        _studentCheck.value = false
        _teacherCheck.value = true
        setWidgetType(WidgetType.TEACHER.name)
    }

}