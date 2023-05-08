package com.myongsik.myongsikandroid.di

import com.myongsik.myongsikandroid.data.datasource.restaurant.RestaurantDataSource
import com.myongsik.myongsikandroid.data.datasource.restaurant.RestaurantDataSourceImpl
import com.myongsik.myongsikandroid.data.repository.food.FoodRepository
import com.myongsik.myongsikandroid.data.repository.food.FoodRepositoryImpl
import com.myongsik.myongsikandroid.domain.repository.restaurant.RestaurantRepository
import com.myongsik.myongsikandroid.data.repository.restaurant.RestaurantRepositoryImpl
import com.myongsik.myongsikandroid.data.repository.search.SearchFoodRepository
import com.myongsik.myongsikandroid.data.repository.search.SearchFoodRepositoryImpl
import com.myongsik.myongsikandroid.domain.repository.user.UserRepository
import com.myongsik.myongsikandroid.data.repository.user.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    //Repository
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
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Singleton
    @Binds
    abstract fun bindRestaurantRepository(
        restaurantRepositoryImpl: RestaurantRepositoryImpl
    ): RestaurantRepository

    //DataSource
    @Singleton
    @Binds
    abstract fun bindRestaurantDataSource(
        restaurantDataSource: RestaurantDataSourceImpl
    ): RestaurantDataSource
}