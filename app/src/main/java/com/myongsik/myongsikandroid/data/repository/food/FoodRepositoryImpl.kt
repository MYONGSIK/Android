package com.myongsik.myongsikandroid.data.repository.food


import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.myongsik.myongsikandroid.data.api.HomeFoodApi
import com.myongsik.myongsikandroid.data.model.food.DayFoodResponse
import com.myongsik.myongsikandroid.data.model.food.RankRestaurantResponse
import com.myongsik.myongsikandroid.data.model.food.ResponseOneRestaurant
import com.myongsik.myongsikandroid.data.model.food.WeekFoodResponse
import com.myongsik.myongsikandroid.data.model.review.RequestReviewData
import com.myongsik.myongsikandroid.data.model.review.ResponseReviewData
import com.myongsik.myongsikandroid.data.repository.food.FoodRepositoryImpl.PreferencesKeys.DINNER_EVALUATION
import com.myongsik.myongsikandroid.data.repository.food.FoodRepositoryImpl.PreferencesKeys.DINNER_EVALUATION_H
import com.myongsik.myongsikandroid.data.repository.food.FoodRepositoryImpl.PreferencesKeys.DINNER_EVALUATION_S
import com.myongsik.myongsikandroid.data.repository.food.FoodRepositoryImpl.PreferencesKeys.LUNCH_A_EVALUATION_H
import com.myongsik.myongsikandroid.data.repository.food.FoodRepositoryImpl.PreferencesKeys.LUNCH_A_EVALUATION_S
import com.myongsik.myongsikandroid.data.repository.food.FoodRepositoryImpl.PreferencesKeys.LUNCH_B_EVALUATION
import com.myongsik.myongsikandroid.data.repository.food.FoodRepositoryImpl.PreferencesKeys.LUNCH_EVALUATION
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
    private object PreferencesKeys {
        val LUNCH_EVALUATION = stringPreferencesKey("lunch_evaluation")
        val LUNCH_B_EVALUATION = stringPreferencesKey("lunch_b_evaluation")
        val DINNER_EVALUATION = stringPreferencesKey("dinner_evaluation")
        val DINNER_EVALUATION_S = stringPreferencesKey("dinner_s_evaluation")
        val LUNCH_A_EVALUATION_S = stringPreferencesKey("lunch_s_evaluation")
        val DINNER_EVALUATION_H = stringPreferencesKey("dinner_h_evaluation")
        val LUNCH_A_EVALUATION_H = stringPreferencesKey("lunch_h_evaluation")
    }

    //중식 A, B 석식 평가 저장
    override suspend fun saveLunchEvaluation(type: String, evaluation: String) {
        when (type) {
            "A" -> {
                dataStore.edit { prefs ->
                    prefs[LUNCH_EVALUATION] = evaluation
                }
            }
            "B" -> {
                dataStore.edit { prefs ->
                    prefs[LUNCH_B_EVALUATION] = evaluation
                }
            }
            "D" -> {
                dataStore.edit { prefs ->
                    prefs[DINNER_EVALUATION] = evaluation
                }
            }
            "DS" -> {
                dataStore.edit { prefs ->
                    prefs[DINNER_EVALUATION_S] = evaluation
                }
            }
            "AS" -> {
                dataStore.edit { prefs ->
                    prefs[LUNCH_A_EVALUATION_S] = evaluation
                }
            }
            "AH" -> {
                dataStore.edit { prefs ->
                    prefs[LUNCH_A_EVALUATION_H] = evaluation
                }
            }
            "DH" -> {
                dataStore.edit { prefs ->
                    prefs[DINNER_EVALUATION_H] = evaluation
                }
            }
        }
    }

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

    //DataStore 초기화
    override suspend fun defaultDataStore() {
        dataStore.edit { prefs ->
            prefs[LUNCH_EVALUATION] = ""
        }
        dataStore.edit { prefs ->
            prefs[LUNCH_B_EVALUATION] = ""
        }
        dataStore.edit { prefs ->
            prefs[DINNER_EVALUATION] = ""
        }
        dataStore.edit { prefs ->
            prefs[LUNCH_A_EVALUATION_S] = ""
        }
        dataStore.edit { prefs ->
            prefs[DINNER_EVALUATION_S] = ""
        }
        dataStore.edit { prefs ->
            prefs[LUNCH_A_EVALUATION_H] = ""
        }
        dataStore.edit { prefs ->
            prefs[DINNER_EVALUATION_H] = ""
        }
    }

    override suspend fun getLunchEvaluation(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    exception.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { prefs ->
                prefs[LUNCH_EVALUATION] ?: ""
            }
    }

    override suspend fun getLunchBEvaluation(): Flow<String> {
        return dataStore.data //data 메서드
            //실패 했을 대비에 예외처리
            .catch { exception ->
                if (exception is IOException) {
                    exception.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { prefs ->
                prefs[LUNCH_B_EVALUATION] ?: ""
            }
    }

    override suspend fun getDinnerEvaluation(): Flow<String> {
        return dataStore.data //data 메서드
            //실패 했을 대비에 예외처리
            .catch { exception ->
                if (exception is IOException) {
                    exception.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { prefs ->
                prefs[DINNER_EVALUATION] ?: ""
            }
    }

    override suspend fun getLunchSEvaluation(): Flow<String> {
        return dataStore.data //data 메서드
            //실패 했을 대비에 예외처리
            .catch { exception ->
                if (exception is IOException) {
                    exception.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { prefs ->
                prefs[LUNCH_A_EVALUATION_S] ?: ""
            }
    }

    override suspend fun getDinnerSEvaluation(): Flow<String> {
        return dataStore.data //data 메서드
            //실패 했을 대비에 예외처리
            .catch { exception ->
                if (exception is IOException) {
                    exception.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { prefs ->
                prefs[DINNER_EVALUATION_S] ?: ""
            }
    }

    override suspend fun getLunchHEvaluation(): Flow<String> {
        return dataStore.data //data 메서드
            //실패 했을 대비에 예외처리
            .catch { exception ->
                if (exception is IOException) {
                    exception.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { prefs ->
                prefs[LUNCH_A_EVALUATION_H] ?: ""
            }
    }

    override suspend fun getDinnerHEvaluation(): Flow<String> {
        return dataStore.data //data 메서드
            //실패 했을 대비에 예외처리
            .catch { exception ->
                if (exception is IOException) {
                    exception.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { prefs ->
                prefs[DINNER_EVALUATION_H] ?: ""
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