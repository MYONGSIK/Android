package com.myongsik.myongsikandroid.di

import com.myongsik.data.datasource.food.FoodDataSource
import com.myongsik.data.datasource.food.FoodDataSourceImpl
import com.myongsik.data.datasource.restaurant.RestaurantDataSource
import com.myongsik.data.datasource.restaurant.RestaurantDataSourceImpl
import com.myongsik.data.datasource.user.UserDataSource
import com.myongsik.data.datasource.user.UserDataSourceImpl
import com.myongsik.data.repository.food.FoodRepository
import com.myongsik.data.repository.food.FoodRepositoryImpl
import com.myongsik.data.repository.food.FoodV2RepositoryImpl
import com.myongsik.data.repository.restaurant.RestaurantRepositoryImpl
import com.myongsik.data.repository.search.SearchFoodRepository
import com.myongsik.data.repository.search.SearchFoodRepositoryImpl
import com.myongsik.data.repository.user.UserRepositoryImpl
import com.myongsik.domain.repository.food.FoodV2Repository
import com.myongsik.domain.repository.restaurant.RestaurantRepository
import com.myongsik.domain.repository.user.UserRepository
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