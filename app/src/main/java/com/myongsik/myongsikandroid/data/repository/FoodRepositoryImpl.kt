package com.myongsik.myongsikandroid.data.repository


import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.LiveData
import com.myongsik.myongsikandroid.data.api.RetrofitInstance.api
import com.myongsik.myongsikandroid.data.model.TodayFoodResponse
import com.myongsik.myongsikandroid.data.model.WeekFoodResponse
import com.myongsik.myongsikandroid.data.repository.FoodRepositoryImpl.PreferencesKeys.EVALUATION
import com.myongsik.myongsikandroid.util.FoodEvaluation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import retrofit2.Response
import java.io.IOException

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
        val EVALUATION = stringPreferencesKey("evaluation")
    }

    override suspend fun saveEvaluation(evaluation: String) {
        dataStore.edit { prefs ->
            prefs[EVALUATION] = evaluation
        }
    }

    override suspend fun getEvaluation(): Flow<String> {
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
                prefs[EVALUATION] ?: ""
            }
    }
}