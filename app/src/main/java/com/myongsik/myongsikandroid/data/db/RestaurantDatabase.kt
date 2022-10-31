package com.myongsik.myongsikandroid.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.myongsik.myongsikandroid.data.model.food.FoodResult

@Database(
    entities = [FoodResult::class],
    version = 1,
    exportSchema = false
)
abstract class RestaurantDatabase : RoomDatabase(){

    abstract fun restaurantDao() : RestaurantDao

    companion object{
        @Volatile
        private var INSTANCE : RestaurantDatabase?= null

        private fun buildDatabase(context : Context) : RestaurantDatabase =
            Room.databaseBuilder(
                context.applicationContext,
                RestaurantDatabase::class.java,
                "today_food"
            ).build()

        fun getInstance(context : Context) : RestaurantDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

    }
}