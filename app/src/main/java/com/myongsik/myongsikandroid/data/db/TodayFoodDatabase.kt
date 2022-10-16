package com.myongsik.myongsikandroid.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.myongsik.myongsikandroid.data.model.FoodResult

@Database(
    entities = [FoodResult::class],
    version = 1,
    exportSchema = false
) //사용하고 싶을 때 알아서 사용함
abstract class TodayFoodDatabase : RoomDatabase(){

    abstract fun todayFoodDao() : TodayFoodDao

    companion object{
        @Volatile
        private var INSTANCE : TodayFoodDatabase?= null

        private fun buildDatabase(context : Context) : TodayFoodDatabase =
            Room.databaseBuilder(
                context.applicationContext,
                TodayFoodDatabase::class.java,
                "today_food"
            ).build()

        fun getInstance(context : Context) : TodayFoodDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

    }
}