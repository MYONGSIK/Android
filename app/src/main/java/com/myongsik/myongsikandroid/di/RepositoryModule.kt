package com.myongsik.myongsikandroid.di

import com.myongsik.myongsikandroid.data.datasource.food.FoodDataSource
import com.myongsik.myongsikandroid.data.datasource.food.FoodDataSourceImpl
import com.myongsik.myongsikandroid.data.datasource.restaurant.RestaurantDataSource
import com.myongsik.myongsikandroid.data.datasource.restaurant.RestaurantDataSourceImpl
import com.myongsik.myongsikandroid.data.datasource.user.UserDataSource
import com.myongsik.myongsikandroid.data.datasource.user.UserDataSourceImpl
import com.myongsik.myongsikandroid.data.repository.food.FoodRepository
import com.myongsik.myongsikandroid.data.repository.food.FoodRepositoryImpl
import com.myongsik.myongsikandroid.data.repository.food.FoodV2RepositoryImpl
import com.myongsik.myongsikandroid.domain.repository.restaurant.RestaurantRepository
import com.myongsik.myongsikandroid.data.repository.restaurant.RestaurantRepositoryImpl
import com.myongsik.myongsikandroid.data.repository.search.SearchFoodRepository
import com.myongsik.myongsikandroid.data.repository.search.SearchFoodRepositoryImpl
import com.myongsik.myongsikandroid.domain.repository.user.UserRepository
import com.myongsik.myongsikandroid.data.repository.user.UserRepositoryImpl
import com.myongsik.myongsikandroid.domain.repository.food.FoodV2Repository
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

    @Singleton
    @Binds
    abstract fun bindFoodV2Repository(
        foodV2RepositoryImpl: FoodV2RepositoryImpl
    ): FoodV2Repository

    //DataSource
    @Singleton
    @Binds
    abstract fun bindRestaurantDataSource(
        restaurantDataSource: RestaurantDataSourceImpl
    ): RestaurantDataSource

    @Singleton
    @Binds
    abstract fun bindUserDataSource(
        userDataSourceImpl: UserDataSourceImpl
    ): UserDataSource

    @Singleton
    @Binds
    abstract fun bindFoodV2DataSource(
        foodDataSourceImpl: FoodDataSourceImpl
    ): FoodDataSource
}