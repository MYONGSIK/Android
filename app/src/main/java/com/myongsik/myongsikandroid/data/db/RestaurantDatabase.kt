package com.myongsik.myongsikandroid.data.db

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

    //-> 의존성 주입
//    companion object{
//        @Volatile
//        private var INSTANCE : RestaurantDatabase?= null
//
//        private fun buildDatabase(context : Context) : RestaurantDatabase =
//            Room.databaseBuilder(
//                context.applicationContext,
//                RestaurantDatabase::class.java,
//                "love_list"
//            ).build()
//
//        fun getInstance(context : Context) : RestaurantDatabase =
//            INSTANCE ?: synchronized(this) {
//                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
//            }
//
//    }
}