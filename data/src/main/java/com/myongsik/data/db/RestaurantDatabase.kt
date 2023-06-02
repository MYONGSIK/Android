package com.myongsik.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.myongsik.myongsikandroid.data.model.kakao.Restaurant

@Database(
    entities = [Restaurant::class],
    version = 1,
    exportSchema = false
)
abstract class RestaurantDatabase : RoomDatabase(){

    abstract fun restaurantDao() : RestaurantDao
}