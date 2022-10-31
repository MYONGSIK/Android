//package com.myongsik.myongsikandroid.ui.viewmodel
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import com.myongsik.myongsikandroid.data.repository.food.FoodRepository
//
//@Suppress("UNCHECKED_CAST")
//class MainViewModelProviderFactory(
//    private val foodRepository: FoodRepository
//) : ViewModelProvider.Factory{
//
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        if(modelClass.isAssignableFrom(MainViewModel::class.java)) {
//            return MainViewModel(foodRepository) as T
//        }
//        throw IllegalArgumentException("viewmodel name is error")
//    }
//}