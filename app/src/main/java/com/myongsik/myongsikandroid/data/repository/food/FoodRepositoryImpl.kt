package com.myongsik.myongsikandroid.data.repository.food


import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.myongsik.myongsikandroid.data.api.HomeFoodApi
import com.myongsik.myongsikandroid.data.db.RestaurantDatabase
import com.myongsik.myongsikandroid.data.model.food.*
import com.myongsik.myongsikandroid.data.model.restaurant.RequestScrap
import com.myongsik.myongsikandroid.data.model.restaurant.ResponseScrap
import com.myongsik.myongsikandroid.data.model.review.RequestReviewData
import com.myongsik.myongsikandroid.data.model.review.ResponseReviewData
import com.myongsik.myongsikandroid.util.DataStoreKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val api: HomeFoodApi,
) : FoodRepository {

    override suspend fun weekGetFoodArea(s: String): Response<WeekFoodResponse> {
        return api.weekGetFoodArea(s)
    }

    override suspend fun dayGetFoodArea(area: String): Response<DayFoodResponse> {
        return api.dayGetFoodArea(area)
    }

    override suspend fun postReview(requestReviewData: RequestReviewData): Response<ResponseReviewData> {
        return api.postReview(requestReviewData)
    }

    override suspend fun getRankRestaurant(sort: String, campus: String, size : Int): Response<RankRestaurantResponse> {
        return api.getRankRestaurant(sort, campus, size)
    }

    override suspend fun getOneRestaurant(storeId: Int): Response<ResponseOneRestaurant> {
        return api.getOneRestaurant(storeId)
    }

    //DataStore
    override suspend fun saveSortType(key: Preferences.Key<String>, value: String) {
        dataStore.edit { prefs ->
            prefs[key] = value
        }
    }

    override suspend fun saveWidgetType(type: String) {
        dataStore.edit { prefs ->
            prefs[DataStoreKey.WIDGET_TYPE] = type
        }
    }

    override suspend fun getCurrentSortType(): Flow<String> {
        return dataStore.data //data 메서드
            .catch { exception ->
                if (exception is IOException) {
                    exception.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { prefs ->
                prefs[DataStoreKey.SORT_TYPE] ?: ""
            }
    }

    override suspend fun getCurrentWidgetType(): Flow<String?> {
        return dataStore.data //data 메서드
            .catch { exception ->
                if (exception is IOException) {
                    exception.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { prefs ->
                prefs[DataStoreKey.WIDGET_TYPE]
            }
    }
}