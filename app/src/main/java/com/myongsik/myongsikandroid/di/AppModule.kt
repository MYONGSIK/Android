package com.myongsik.myongsikandroid.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.myongsik.myongsikandroid.data.api.HomeFoodApi
import com.myongsik.myongsikandroid.data.api.RestaurantApi
import com.myongsik.myongsikandroid.data.api.SearchFoodApi
import com.myongsik.myongsikandroid.data.api.UserApi
import com.myongsik.myongsikandroid.data.db.RestaurantDatabase
import com.myongsik.myongsikandroid.util.Constant
import com.myongsik.myongsikandroid.util.Constant.DATASTORE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    //Retrofit
    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .build();
    }

    @Singleton
    @Provides
    fun provideHomeFoodApi(okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory): HomeFoodApi {
        return Retrofit.Builder()
            .baseUrl(Constant.MYONG_SIK_BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
            .create()
    }

    @Singleton
    @Provides
    fun provideSearchFoodApi(okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory): SearchFoodApi {
        return Retrofit.Builder()
            .baseUrl(Constant.KAKAO_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create()
    }

    @Singleton
    @Provides
    fun provideUserApi(okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory): UserApi {
        return Retrofit.Builder()
            .baseUrl(Constant.MYONG_SIK_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create()
    }

    @Singleton
    @Provides
    fun provideRestaurantApi(okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory): RestaurantApi {
        return Retrofit.Builder()
            .baseUrl(Constant.MYONG_SIK_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create()
    }
    
    //Room
    @Singleton
    @Provides
    fun provideBookSearchDatabase(@ApplicationContext context: Context): RestaurantDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            RestaurantDatabase::class.java,
            "love_list"
        ).build()

    //DataStore
    @Singleton
    @Provides
    fun providePreferencesDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile(DATASTORE_NAME) }
        )

}