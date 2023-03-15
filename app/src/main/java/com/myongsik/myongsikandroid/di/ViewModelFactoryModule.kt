package com.myongsik.myongsikandroid.di

import com.myongsik.myongsikandroid.data.repository.search.SearchFoodRepository
import com.myongsik.myongsikandroid.ui.viewmodel.SearchViewModelProviderFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ViewModelFactoryModule {

    @Provides
    @Singleton
    fun provideSearchViewModelImplFactory(
        searchFoodRepository: SearchFoodRepository
    ): SearchViewModelProviderFactory {
        return SearchViewModelProviderFactory(searchFoodRepository)
    }
}