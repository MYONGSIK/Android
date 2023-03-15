package com.myongsik.myongsikandroid.di

import androidx.lifecycle.ViewModelProvider
import com.myongsik.myongsikandroid.data.repository.food.FoodRepository
import com.myongsik.myongsikandroid.data.repository.food.FoodRepositoryImpl
import com.myongsik.myongsikandroid.data.repository.search.SearchFoodRepository
import com.myongsik.myongsikandroid.data.repository.search.SearchFoodRepositoryImpl
import com.myongsik.myongsikandroid.ui.viewmodel.SearchViewModelProviderFactory
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindFoodRepository(
        foodRepositoryImpl: FoodRepositoryImpl,
    ) : FoodRepository

    @Singleton
    @Binds
    abstract fun bindSearchFoodRepository(
        searchFoodRepositoryImpl: SearchFoodRepositoryImpl
    ): SearchFoodRepository

    @Singleton
    @Binds
    abstract fun bindSearchViewModelFactory(
        viewModelFactory: SearchViewModelProviderFactory
    ): ViewModelProvider.Factory
}