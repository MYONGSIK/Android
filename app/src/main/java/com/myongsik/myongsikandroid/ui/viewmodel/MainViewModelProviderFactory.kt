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

//MainViewModel 은 의존성 주입으로 인해 제외
//API BaseURL 을 두 개를 써야하는데, Retrofit Instance 두 개 의존성 주입하는 방법을 못찾아서
//SearchViewModel 은 의존성 주입을 안하고 기존의 팩토리 패턴으로 작성