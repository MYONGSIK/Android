package com.myongsik.myongsikandroid.data.repository


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.myongsik.myongsikandroid.data.api.RetrofitInstance.api
import com.myongsik.myongsikandroid.data.model.FoodResult
import com.myongsik.myongsikandroid.data.model.TodayFoodResponse
import com.myongsik.myongsikandroid.data.model.WeekFoodResponse
import com.myongsik.myongsikandroid.data.repository.FoodRepositoryImpl.PreferencesKeys.DINNER_EVALUATION
import com.myongsik.myongsikandroid.data.repository.FoodRepositoryImpl.PreferencesKeys.LUNCH_B_EVALUATION
import com.myongsik.myongsikandroid.data.repository.FoodRepositoryImpl.PreferencesKeys.LUNCH_EVALUATION
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import retrofit2.Response
import java.io.IOException
import java.time.LocalDate

class FoodRepositoryImpl(
    private val dataStore: DataStore<Preferences>,
) : FoodRepository {

    //오늘 식단 조회
    override suspend fun todayGetFood(): Response<TodayFoodResponse> {
        return api.todayGetFood()
    }

    //주간 식단 조회
    override suspend fun weekGetFood(): Response<WeekFoodResponse> {
        return api.weekGetFood()
    }

    //DataStore
    private object PreferencesKeys {
        //저장, 불러올 키를 정의, String 사용
        //중식 평가 키
        val LUNCH_EVALUATION = stringPreferencesKey("lunch_evaluation")

        //중식 B 평가 키
        val LUNCH_B_EVALUATION = stringPreferencesKey("lunch_b_evaluation")

        //석식 평가 키
        val DINNER_EVALUATION = stringPreferencesKey("dinner_evaluation")
    }

    override suspend fun saveLunchEvaluation(foodResult: FoodResult, evaluation: String) {
        when (foodResult.type) {
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
            else -> {
                dataStore.edit { prefs ->
                    prefs[DINNER_EVALUATION] = evaluation
                }
            }
        }
    }

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
    }

    override suspend fun getLunchEvaluation(): Flow<String> {
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
}