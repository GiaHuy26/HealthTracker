package com.example.healthtracker.domain.repository

import android.content.Context
import com.example.healthtracker.data.local.db.entity.FoodEntity
import com.example.healthtracker.data.local.db.entity.MealEntity
import kotlinx.coroutines.flow.Flow

interface FoodDiaryRepository {
    fun getMeal(userEmail: String, date: String): Flow<List<MealEntity>>
    fun getMealsBetweenDates(
        userEmail: String,
        startDate: String,
        endDate: String
    ): Flow<List<MealEntity>>
    suspend fun searchFoods(query: String): List<FoodEntity>
    suspend fun addMeal(meal: MealEntity)
    suspend fun deleteMeal(meal: MealEntity)
    suspend fun seedSampleFood(context: Context, languageCode: String, userEmail: String)
    suspend fun getMealsByDateAndType(
        userEmail: String,
        date: String,
        mealType: String
    ): List<MealEntity>
    suspend fun deleteMealsByType(userEmail: String, date: String, mealType: String)
}
