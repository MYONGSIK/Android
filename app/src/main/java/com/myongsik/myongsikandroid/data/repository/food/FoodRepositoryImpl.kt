package com.myongsik.myongsikandroid.data.repository.food


import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.myongsik.myongsikandroid.data.api.HomeFoodApi
import com.myongsik.myongsikandroid.data.db.RestaurantDatabase
import com.myongsik.myongsikandroid.data.model.food.FoodResult
import com.myongsik.myongsikandroid.data.model.food.WeekFoodResponse
import com.myongsik.myongsikandroid.data.model.kakao.Restaurant
import com.myongsik.myongsikandroid.data.model.review.RequestReviewData
import com.myongsik.myongsikandroid.data.model.review.ResponseReviewData
import com.myongsik.myongsikandroid.data.model.user.RequestUserData
import com.myongsik.myongsikandroid.data.model.user.ResponseUserData
import com.myongsik.myongsikandroid.data.repository.food.FoodRepositoryImpl.PreferencesKeys.DINNER_EVALUATION
import com.myongsik.myongsikandroid.data.repository.food.FoodRepositoryImpl.PreferencesKeys.DINNER_EVALUATION_S
import com.myongsik.myongsikandroid.data.repository.food.FoodRepositoryImpl.PreferencesKeys.LUNCH_A_EVALUATION_S
import com.myongsik.myongsikandroid.data.repository.food.FoodRepositoryImpl.PreferencesKeys.LUNCH_B_EVALUATION
import com.myongsik.myongsikandroid.data.repository.food.FoodRepositoryImpl.PreferencesKeys.LUNCH_EVALUATION
import com.myongsik.myongsikandroid.util.Constant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/*
FoodRepository 를 구현한 FoodRepositoryImpl
Hilt 주입 완료
 */
@Singleton
class FoodRepositoryImpl @Inject constructor(
    private val db : RestaurantDatabase,
    private val dataStore: DataStore<Preferences>,
    private val api : HomeFoodApi,
) : FoodRepository {

    override suspend fun weekGetFoodArea(s:String): Response<WeekFoodResponse> {
        return api.weekGetFoodArea(s)
    }

    override suspend fun postReview(requestReviewData: RequestReviewData): Response<ResponseReviewData> {
        return api.postReview(requestReviewData)
    }

    override suspend fun postUser(requestUserData: RequestUserData): Response<ResponseUserData> {
        return api.postUser(requestUserData)
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

        //석식 평가 키
        val DINNER_EVALUATION_S = stringPreferencesKey("dinner_s_evaluation")

        // 중식 평가 키
        val LUNCH_A_EVALUATION_S = stringPreferencesKey("lunch_s_evaluation")

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
    }

    //중식 A 조회
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

    //중식 B 조회
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

    //석식 조회
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


    //석식 조회
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

    //석식 조회
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

    //장소 찜꽁리스트에 저장
    override suspend fun insertFoods(restaurant: Restaurant) {
        db.restaurantDao().insertGoodFood(restaurant)
    }

    //장소 찜콩리스트에서 삭제
    override suspend fun deleteFoods(restaurant: Restaurant) {
        db.restaurantDao().deleteBook(restaurant)
    }

    //장소 현재 찜해두었는지 판단
    override fun loveIs(id: String): Restaurant {
        return db.restaurantDao().loveIs(id)
    }

    override fun updateLove(id: String): Boolean {
        if(!db.restaurantDao().loveUpdate(id)){
            return false
        }
        return true
    }

    //음식 조회 페이징 처리
    override fun getFoods(): Flow<PagingData<Restaurant>> {
        val pagingSourceFactory = { db.restaurantDao().getFoods() }

        return Pager(
            config = PagingConfig(
                pageSize = Constant.PAGING_SIZE,
                enablePlaceholders = false,
                maxSize = Constant.PAGING_SIZE * 3
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }


}